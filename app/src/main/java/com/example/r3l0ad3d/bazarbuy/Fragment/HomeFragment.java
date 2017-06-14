package com.example.r3l0ad3d.bazarbuy.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.r3l0ad3d.bazarbuy.Adapter.ProductAdapterRecycleView;
import com.example.r3l0ad3d.bazarbuy.Interface.UserProductList;
import com.example.r3l0ad3d.bazarbuy.ModelClass.LocalDataSave;
import com.example.r3l0ad3d.bazarbuy.ModelClass.Product;
import com.example.r3l0ad3d.bazarbuy.ModelClass.UserInformation;
import com.example.r3l0ad3d.bazarbuy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{


    private List<Product> productList;
    private List<Product> userProductList;

    private ProductAdapterRecycleView adapterRecycleView;
    private RecyclerView recyclerView;
    private ProgressDialog pd;

    private DatabaseReference ref;
    private String userId;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvProductList);
        pd = new ProgressDialog(getContext());


        ref = FirebaseDatabase.getInstance().getReference("root").child("products");
        ref.keepSynced(true);

        productList = new ArrayList<>();
        userProductList = new ArrayList<>();
        /*Bundle bundle = getArguments();
        userId = bundle.getString("userId");*/

        LocalDataSave db = new LocalDataSave(getContext());
        userId = db.getUserId();
        //db.print();
        if(db.notNull()){
            UserInformation uInfo= db.getData();
            //Toast.makeText(getContext(),uInfo.getUserId()+" "+uInfo.getUserFullName(),Toast.LENGTH_LONG).show();
        }


        adapterRecycleView = new ProductAdapterRecycleView(getContext(),productList,"");
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterRecycleView);

        pd.show();
        dataset();
        pd.dismiss();
        return view;
    }

    private void dataset() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                userProductList.clear();
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
