package com.example.r3l0ad3d.bazarbuy;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.r3l0ad3d.bazarbuy.Adapter.PageAdapter;
import com.example.r3l0ad3d.bazarbuy.Adapter.TrashPageAdapter;
import com.example.r3l0ad3d.bazarbuy.Adapter.UserPageAdapter;
import com.example.r3l0ad3d.bazarbuy.ModelClass.LocalDataSave;
import com.example.r3l0ad3d.bazarbuy.ModelClass.Product;
import com.example.r3l0ad3d.bazarbuy.ModelClass.UserInformation;
import com.example.r3l0ad3d.bazarbuy.databinding.ActivityProductShowBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductShowActivity extends AppCompatActivity {

    private ActivityProductShowBinding binding;
    private List<Product> productList = new ArrayList<>();

    private PageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Product Show");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_product_show);
        productList = (List<Product>) getIntent().getSerializableExtra("productList");
        String flag = getIntent().getStringExtra("from");
        if(flag.equals("trash")){
            TrashPageAdapter adapter2 = new TrashPageAdapter(getApplicationContext(),productList);
            binding.viewPagerProduct.setAdapter(adapter2);
        }else if(flag.equals("userList")){
            UserPageAdapter adapter1 = new UserPageAdapter(getApplicationContext(),productList);
            binding.viewPagerProduct.setAdapter(adapter1);
        }else {
            adapter = new PageAdapter(getApplicationContext(),productList);
            binding.viewPagerProduct.setAdapter(adapter);
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
        if (id == R.id.home_menu) {
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            //return true;
        }else if(id==R.id.logout_menu){
            LocalDataSave db = new LocalDataSave(getApplicationContext());
            if(db.notNull()){
                UserInformation info = db.getData();
                info.setLogIn(false);
                db.setData(info);
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
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
}
