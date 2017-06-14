package com.example.r3l0ad3d.bazarbuy.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.r3l0ad3d.bazarbuy.ModelClass.EncoderDecoder;
import com.example.r3l0ad3d.bazarbuy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NiL-AkAsH on 6/6/2017.
 */

public class ImageAdapter extends PagerAdapter {

    private Context context;
    private List<String> imgList = new ArrayList<>();

    public ImageAdapter(Context context, List<String> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_view_pls,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageItem);

       Bitmap bitmap =  EncoderDecoder.ImageDecoded(imgList.get(position));

        imageView.setImageBitmap(bitmap);

        view.setTag(imgList.get(position));

        return view;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o==view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        String s= (String) ((View)object).getTag();
        int i = imgList.indexOf(s);
        if(i>=0) return i;
        else return POSITION_NONE;
    }
}
