package com.example.r3l0ad3d.bazarbuy.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.r3l0ad3d.bazarbuy.ModelClass.EncoderDecoder;
import com.example.r3l0ad3d.bazarbuy.ModelClass.Product;
import com.example.r3l0ad3d.bazarbuy.ProductShowActivity;
import com.example.r3l0ad3d.bazarbuy.R;
import com.example.r3l0ad3d.bazarbuy.databinding.ProductItemBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiL-AkAsH on 6/5/2017.
 */

public class ProductAdapterRecycleView extends RecyclerView.Adapter<ProductAdapterRecycleView.ViewHolder> {

    private Context context;
    private List<Product> productList = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private DatabaseReference refPro;
    private String flag;


    public ProductAdapterRecycleView(Context context, List<Product> productList,String flag) {
        this.context = context;
        this.productList = productList;
        this.flag=flag;
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("root").child("trash");
        refPro = database.getReference("root").child("products");
    }

    @Override
    public ProductAdapterRecycleView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapterRecycleView.ViewHolder holder, int position) {
        holder.binding.tvProductNamePI.setText(productList.get(position).getProductName());
        holder.binding.tvProductCatagoryPI.setText(productList.get(position).getProductCatagory());
        holder.binding.tvProductPricePI.setText(productList.get(position).getProductPrice());
        holder.binding.tvLocationPI.setText(productList.get(position).getOwnerLocation());
        Bitmap bitmap = EncoderDecoder.ImageDecoded(productList.get(position).getProductImageTwo());
        holder.binding.ivProductImagePI.setImageBitmap(bitmap);

        setItemClick(holder.itemView,position,flag);


    }




    private void setItemClick(View itemView, final int position,String flag) {
        if(flag.equals("trash")){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeList(position);
                    Intent intent = new Intent(context, ProductShowActivity.class);
                    intent.putExtra("productList", (Serializable) productList);
                    intent.putExtra("from","trash");
                    context.startActivity(intent);
                    //Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();
                }
            });

        }else if(flag.equals("userList")){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeList(position);
                    Intent intent = new Intent(context, ProductShowActivity.class);
                    intent.putExtra("productList", (Serializable) productList);
                    intent.putExtra("from","userList");
                    context.startActivity(intent);
                    //Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();
                }
            });
        }else {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeList(position);
                    Intent intent = new Intent(context, ProductShowActivity.class);
                    intent.putExtra("productList", (Serializable) productList);
                    intent.putExtra("from","");
                    context.startActivity(intent);
                    //Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void removeList(int position) {
        for (int i=0;i<position;i++){
            Product pro = productList.get(i);
            productList.remove(i);
            productList.add(pro);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProductItemBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}