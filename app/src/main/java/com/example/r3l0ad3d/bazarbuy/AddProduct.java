package com.example.r3l0ad3d.bazarbuy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.r3l0ad3d.bazarbuy.ModelClass.EncoderDecoder;
import com.example.r3l0ad3d.bazarbuy.ModelClass.LocalDataSave;
import com.example.r3l0ad3d.bazarbuy.ModelClass.Product;
import com.example.r3l0ad3d.bazarbuy.ModelClass.UserInformation;
import com.example.r3l0ad3d.bazarbuy.databinding.ActivityAddProductBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddProduct extends AppCompatActivity {

    private static final int RESULT_LOAD_IMG = 1111;
    private static final String ERROR = "Empty Field Found";


    private ActivityAddProductBinding binding ;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;

    private String imageOne;
    private String imageTwo;
    private String imageThree;
    private String userId;

    private boolean imgFlagOne = false;
    private boolean imgFlagTwo = false;
    private boolean imgFlagThree = false;

    private UserInformation uInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Product");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_product);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("root").child("products");
        ref.keepSynced(true);

        uInfo = new UserInformation();

        LocalDataSave db = new LocalDataSave(getApplicationContext());
        if(db.notNull()){
            uInfo = db.getData();
            userId = uInfo.getUserId();

        }else {
            userId = "null";
        }
        Toast.makeText(getApplicationContext(),userId,Toast.LENGTH_LONG).show();
    }


    public void ImageOneClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        imgFlagOne=true;
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

    }

    public void ImageTwoClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        imgFlagTwo=true;
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    public void ImageThreeClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        imgFlagThree=true;
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                if(imgFlagOne){
                    imgFlagOne=false;
                    binding.ibtnImageOnePA.setImageBitmap(selectedImage);
                    imageOne= EncoderDecoder.ImageEncoded(selectedImage);
                }else if(imgFlagTwo){
                    imgFlagTwo=false;
                    binding.ibtnImageTwoPA.setImageBitmap(selectedImage);
                    imageTwo=EncoderDecoder.ImageEncoded(selectedImage);
                }else if(imgFlagThree){
                    imgFlagThree=false;
                    binding.ibtnImageThreePA.setImageBitmap(selectedImage);
                    imageThree=EncoderDecoder.ImageEncoded(selectedImage);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


    public void AddProductClick(View view) {
        String productName = binding.etProductNamePA.getText().toString().trim();
        String productDiscription = binding.etProductDescriptionPA.getText().toString().trim();
        String productQuentity = binding.etProductQuantityPA.getText().toString().trim();
        String productPrice = binding.etProductPricePA.getText().toString().trim();
        String productCategory = binding.etProductCatagoryPA.getText().toString().trim();
        String location = binding.etLocationPA.getText().toString().trim();
        long t = System.currentTimeMillis();
        String time = String.valueOf(t);

        if (productName.equals("")) {
            binding.etProductNamePA.setError(ERROR);
        }else if(productDiscription.equals("")){
            binding.etProductDescriptionPA.setError(ERROR);
        }else if(productCategory.equals("")){
            binding.etProductCatagoryPA.setError(ERROR);
        }else if(productQuentity.equals("")){
            binding.etProductQuantityPA.setError(ERROR);
        }else if(productPrice.equals("")){
            binding.etProductPricePA.setError(ERROR);
        }else if(location.equals("")){
            binding.etLocationPA.setError(ERROR);
        }else if(imageOne.equals("")||imageTwo.equals("")||imageThree.equals("")){
            Toast.makeText(getApplicationContext(),"Please upload three image",Toast.LENGTH_LONG).show();
        }else if(!userId.equals("")){
            Product product = new Product();
            String productId = ref.push().getKey();
            product.setUserId(userId);
            product.setOwnerName(uInfo.getUserFullName());
            product.setOwnerMobileNo(uInfo.getUserMobileNo());
            product.setOwnerEmailNo(uInfo.getUserEmail());
            product.setCreateTime(time);
            product.setProductId(productId);
            product.setProductName(productName);
            product.setProductCatagory(productCategory);
            product.setProductDetails(productDiscription);
            product.setProductPrice(productPrice);
            product.setProductQuentity(productQuentity);
            product.setOwnerLocation(location);
            product.setProductImageOne(imageOne);
            product.setProductImageTwo(imageTwo);
            product.setProductImageThree(imageThree);
            ref.child(productId).setValue(product);
            Toast.makeText(getApplicationContext(),"Product Add Successfully",Toast.LENGTH_LONG).show();
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
