package com.example.r3l0ad3d.bazarbuy.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.r3l0ad3d.bazarbuy.ModelClass.EncoderDecoder;
import com.example.r3l0ad3d.bazarbuy.ModelClass.Product;
import com.example.r3l0ad3d.bazarbuy.R;
import com.example.r3l0ad3d.bazarbuy.databinding.ProductItemShowBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiL-AkAsH on 6/5/2017.
 */
public class PageAdapter extends PagerAdapter {

    private Context context;
    private List<Product> data = new ArrayList<>();

    public PageAdapter(Context context, List<Product> data) {
        this.context = context;
        this.data = data;
    }


    //View generate and populate
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.product_item_show,container,false);
        ProductItemShowBinding binding = DataBindingUtil.bind(view);

        binding.tvCategoryPIS.setText(data.get(position).getProductCatagory());
        binding.tvProductNamePIS.setText(data.get(position).getProductName());
        binding.tvDescriptionPIS.setText(data.get(position).getProductDetails());
        binding.tvProductPricePIS.setText(data.get(position).getProductPrice());
        binding.tvProductQuentityPIS.setText(data.get(position).getProductQuentity());
        binding.tvLocationPIS.setText(data.get(position).getOwnerLocation());
        binding.tvOwnerEmailPIS.setText(data.get(position).getOwnerEmailNo());
        binding.tvOwnerMobileNoPIS.setText(data.get(position).getOwnerMobileNo());
        binding.tvOwnerNamePIS.setText(data.get(position).getOwnerName());

        Bitmap bitmap = EncoderDecoder.ImageDecoded(data.get(position).getProductImageOne());
        binding.ivImageOnePIS.setImageBitmap(bitmap);
        bitmap = EncoderDecoder.ImageDecoded(data.get(position).getProductImageTwo());
        binding.ivImageTwoPIS.setImageBitmap(bitmap);
        bitmap = EncoderDecoder.ImageDecoded(data.get(position).getProductImageThree());
        binding.ivImageThreePIS.setImageBitmap(bitmap);

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

