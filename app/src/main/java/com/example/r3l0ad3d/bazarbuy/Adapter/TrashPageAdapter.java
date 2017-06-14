package com.example.r3l0ad3d.bazarbuy.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.r3l0ad3d.bazarbuy.ModelClass.EncoderDecoder;
import com.example.r3l0ad3d.bazarbuy.ModelClass.Product;
import com.example.r3l0ad3d.bazarbuy.R;
import com.example.r3l0ad3d.bazarbuy.databinding.ProductItemShowBinding;
import com.example.r3l0ad3d.bazarbuy.databinding.TrashProductListShowBinding;
import com.example.r3l0ad3d.bazarbuy.databinding.UserProductListShowBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiL-AkAsH on 6/5/2017.
 */
public class TrashPageAdapter extends PagerAdapter {

    private Context context;
    private List<Product> data = new ArrayList<>();

    public TrashPageAdapter(Context context, List<Product> data) {
        this.context = context;
        this.data = data;
    }


    //View generate and populate
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.trash_product_list_show,container,false);
        TrashProductListShowBinding binding = DataBindingUtil.bind(view);

        binding.tvCategoryTPIS.setText(data.get(position).getProductCatagory());
        binding.tvProductNameTPIS.setText(data.get(position).getProductName());
        binding.tvDescriptionTPIS.setText(data.get(position).getProductDetails());
        binding.tvProductPriceTPIS.setText(data.get(position).getProductPrice());
        binding.tvProductQuentityTPIS.setText(data.get(position).getProductQuentity());
        binding.tvLocationTPIS.setText(data.get(position).getOwnerLocation());
        binding.tvOwnerEmailTPIS.setText(data.get(position).getOwnerEmailNo());
        binding.tvOwnerMobileNoTPIS.setText(data.get(position).getOwnerMobileNo());
        binding.tvOwnerNameTPIS.setText(data.get(position).getOwnerName());

        Bitmap bitmap = EncoderDecoder.ImageDecoded(data.get(position).getProductImageOne());
        binding.ivImageOneTPIS.setImageBitmap(bitmap);
        bitmap = EncoderDecoder.ImageDecoded(data.get(position).getProductImageTwo());
        binding.ivImageTwoTPIS.setImageBitmap(bitmap);
        bitmap = EncoderDecoder.ImageDecoded(data.get(position).getProductImageThree());
        binding.ivImageThreeTPIS.setImageBitmap(bitmap);

        binding.btnRecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("root").child("trash")
                        .child(data.get(position).getUserId()).child(data.get(position).getProductId());
                ref.removeValue();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("root")
                        .child("products").child(data.get(position).getProductId());
                reference.setValue(data.get(position));
                data.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context,"Product Recycle",Toast.LENGTH_LONG).show();
            }
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("root").child("trash")
                        .child(data.get(position).getUserId()).child(data.get(position).getProductId());
                ref.removeValue();
                data.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context,"Product Removed",Toast.LENGTH_LONG).show();
            }
        });

        view.setTag(data.get(position));

        container.addView(view);

        return view;
    }

    //item count
    @Override
    public int getCount() {
        return data.size();
    }

    //is view avail avail
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //get item position
    @Override
    public int getItemPosition(Object object) {
        Product p = (Product) ((View)object).getTag();
        int i = data.indexOf(p);
        if(i>=0) return i;
        else return POSITION_NONE;
    }
}

