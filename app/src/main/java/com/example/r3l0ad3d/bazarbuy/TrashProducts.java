package com.example.r3l0ad3d.bazarbuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.r3l0ad3d.bazarbuy.Adapter.ProductAdapterRecycleView;
import com.example.r3l0ad3d.bazarbuy.ModelClass.LocalDataSave;
import com.example.r3l0ad3d.bazarbuy.ModelClass.Product;
import com.example.r3l0ad3d.bazarbuy.ModelClass.UserInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrashProducts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapterRecycleView adapter;
    private List<Product> productList;
    private String userId;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Trash Products");
        setContentView(R.layout.activity_trash_products);
        recyclerView = (RecyclerView) findViewById(R.id.rvTrashList);

        LocalDataSave db = new LocalDataSave(getApplicationContext());
        userId = db.getUserId();
        database = FirebaseDatabase.getInstance();
        //database.setPersistenceEnabled(true);
        ref = database.getReference("root").child("trash").child(userId);
        ref.keepSynced(true);
        productList = new ArrayList<>();
        adapter = new ProductAdapterRecycleView(getApplicationContext(),productList,"trash");

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        populateData();
    }

    private void populateData() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    productList.add(ds.getValue(Product.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
