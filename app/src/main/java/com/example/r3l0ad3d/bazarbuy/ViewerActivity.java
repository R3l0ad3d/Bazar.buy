package com.example.r3l0ad3d.bazarbuy;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.r3l0ad3d.bazarbuy.Adapter.ProductAdapterRecycleView;
import com.example.r3l0ad3d.bazarbuy.ModelClass.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewerActivity extends AppCompatActivity {

    private List<Product> productList;

    private ProductAdapterRecycleView adapterRecycleView;
    private RecyclerView recyclerView;
    private ProgressDialog pd;

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        recyclerView = (RecyclerView) findViewById(R.id.rvProductListViewer);
        pd = new ProgressDialog(getApplicationContext());

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("root").child("products");
        ref.keepSynced(true);
        productList = new ArrayList<>();

        adapterRecycleView = new ProductAdapterRecycleView(getApplicationContext(),productList,"");
       LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterRecycleView);

        //pd.show();
        dataset();
        //pd.cancel();

    }
    private void dataset() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Product pro = ds.getValue(Product.class);
                    productList.add(pro);
                }
                adapterRecycleView.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
