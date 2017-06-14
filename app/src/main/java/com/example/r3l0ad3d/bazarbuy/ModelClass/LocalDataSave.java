package com.example.r3l0ad3d.bazarbuy.ModelClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

/**
 * Created by NiL-AkAsH on 6/14/2017.
 */

public class LocalDataSave {
    private String userId;
    private Context context;
    private static String USER_DB = "db";
    private static String KEY = "key";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private UserInformation uInfo;

    public LocalDataSave(Context context) {
        this.context = context;
       sharedPreferences = context.getSharedPreferences(USER_DB,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
       // database = FirebaseDatabase.getInstance();
        //ref = database.getReference("root").child("user").child(userId);
        uInfo = new UserInformation();
    }

    public LocalDataSave(Context context, String userId) {
        this.context=context;
        this.userId = userId;
        sharedPreferences = context.getSharedPreferences(USER_DB,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("root").child("user");
        uInfo = new UserInformation();
    }

    public void dataSave(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    uInfo = ds.getValue(UserInformation.class);
                    if(uInfo.getUserId().equals(userId)) {
                        Gson gson = new Gson();
                        String userInfo = gson.toJson(uInfo);
                        editor.putString(KEY,userInfo);
                        editor.commit();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setData(UserInformation userInformation){
        Gson gson = new Gson();
        String userInfo = gson.toJson(userInformation);
        editor.putString(KEY,userInfo);
        editor.commit();
    }

    public boolean notNull(){
        String data =sharedPreferences.getString(KEY,"");
        if(data.equals("")){
            return false;
        }else return true;
    }

    public UserInformation getData(){
        Gson gson = new Gson();
        String data =sharedPreferences.getString(KEY,"");

        UserInformation ui = gson.fromJson(data,UserInformation.class);
        return ui;
    }
    public boolean isLogIn(){
        UserInformation Info = getData();
        if(!notNull()){
            return false;
        }else if (Info.isLogIn()) {
            return true;
        }else return false;
    }

    public String getUserId(){
        if(notNull()){
            UserInformation uin = getData();
            return uin.getUserId();
        }else return "faka";
    }

}
