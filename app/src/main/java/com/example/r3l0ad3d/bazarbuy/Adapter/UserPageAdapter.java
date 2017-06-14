package com.example.r3l0ad3d.bazarbuy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.r3l0ad3d.bazarbuy.EditProductItem;
import com.example.r3l0ad3d.bazarbuy.ModelClass.EncoderDecoder;
import com.example.r3l0ad3d.bazarbuy.ModelClass.Product;
import com.example.r3l0ad3d.bazarbuy.R;
import com.example.r3l0ad3d.bazarbuy.databinding.ProductItemShowBinding;
import com.example.r3l0ad3d.bazarbuy.databinding.UserProductListShowBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiL-AkAsH on 6/5/2017.
 */
public class UserPageAdapter extends PagerAdapter {

    private Context context;
    private List<Product> data = new ArrayList<>();

    public UserPageAdapter(Context context, List<Product> data) {
        this.context = context;
        this.data = data;
    }


    //View generate and populate
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_product_list_show,container,false);
        UserProductListShowBinding binding = DataBindingUtil.bind(view);

        binding.tvCategoryUPIS.setText(data.get(position).getProductCatagory());
        binding.tvProductNameUPIS.setText(data.get(position).getProductName());
        binding.tvDescriptionUPIS.setText(data.get(position).getProductDetails());
        binding.tvProductPriceUPIS.setText(data.get(position).getProductPrice());
        binding.tvProductQuentityUPIS.setText(data.get(position).getProductQuentity());
        binding.tvLocationUPIS.setText(data.get(position).getOwnerLocation());
        binding.tvOwnerEmailUPIS.setText(data.get(position).getOwnerEmailNo());
        binding.tvOwnerMobileNoUPIS.setText(data.get(position).getOwnerMobileNo());
        binding.tvOwnerNameUPIS.setText(data.get(position).getOwnerName());

        Bitmap bitmap = EncoderDecoder.ImageDecoded(data.get(position).getProductImageOne());
        binding.ivImageOneUPIS.setImageBitmap(bitmap);
        bitmap = EncoderDecoder.ImageDecoded(data.get(position).getProductImageTwo());
        binding.ivImageTwoUPIS.setImageBitmap(bitmap);
        bitmap = EncoderDecoder.ImageDecoded(data.get(position).getProductImageThree());
        binding.ivImageThreeUPIS.setImageBitmap(bitmap);

        binding.btnTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("root").child("trash")
                        .child(data.get(position).getUserId()).child(data.get(position).getProductId());
                ref.setValue(data.get(position));
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("root")
                        .child("products").child(data.get(position).getProductId());
                reference.removeValue();
                data.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context,"Move to trash",Toast.LENGTH_LONG).show();
            }
        });
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProductItem.class);
                intent.putExtra("product",data.get(position));
                context.startActivity(intent);
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

