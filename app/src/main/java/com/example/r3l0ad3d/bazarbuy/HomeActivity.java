package com.example.r3l0ad3d.bazarbuy;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.r3l0ad3d.bazarbuy.Fragment.HomeFragment;
import com.example.r3l0ad3d.bazarbuy.Interface.UserProductList;
import com.example.r3l0ad3d.bazarbuy.ModelClass.LocalDataSave;
import com.example.r3l0ad3d.bazarbuy.ModelClass.UserInformation;
import com.example.r3l0ad3d.bazarbuy.databinding.NavHeaderHomeBinding;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String userId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //userId = getIntent().getStringExtra("userId");


        LocalDataSave db = new LocalDataSave(getApplicationContext());
        if(!db.notNull()){
            LocalDataSave dbd = new LocalDataSave(getApplicationContext(),userId);
            dbd.dataSave();
        }
        UserInformation userInformation = db.getData();
        userInformation.setLogIn(true);
        db.setData(userInformation);

        userId = db.getUserId();

        Fragment fragment = new HomeFragment();
       // Bundle bundle = new Bundle();
        //bundle.putString("userId",userId);
        //fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.replace_layout, fragment).commit();
        ft.addToBackStack(null);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddProduct.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        LocalDataSave db = new LocalDataSave(getApplicationContext());
        if(!db.isLogIn()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        super.onStart();

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.home_mav_menu){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userId",userId);
            fragment.setArguments(bundle);
            ft.add(R.id.replace_layout, fragment).commit();
            ft.addToBackStack(null);
        } else if (id == R.id.add_product_nav_menu) {

            Intent intent = new Intent(this,AddProduct.class);
            intent.putExtra("userId",userId);
            startActivity(intent);

        } else if (id == R.id.my_product_list_nav_menu) {
            Intent intent = new Intent(this, UserProductListActivity.class);
            intent.putExtra("userId",userId);
            startActivity(intent);

        } else if (id == R.id.trash_nav_menu) {
            Intent intent = new Intent(this,TrashProducts.class);
            startActivity(intent);

        }else if(id == R.id.logout_nav_menu){
            LocalDataSave db = new LocalDataSave(getApplicationContext());
           if(db.notNull()){
               UserInformation info = db.getData();
               info.setLogIn(false);
               db.setData(info);
               Intent intent = new Intent(this,MainActivity.class);
               startActivity(intent);
           }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
