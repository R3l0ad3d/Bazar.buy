package com.example.r3l0ad3d.bazarbuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.r3l0ad3d.bazarbuy.ModelClass.LocalDataSave;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signInActivity(View view) {
        Intent intent = new Intent(this,SignIn.class);
        startActivity(intent);
    }

    public void signUpActivity(View view) {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }

    public void homeActivity(View view) {
        Intent intent = new Intent(this,ViewerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalDataSave db = new LocalDataSave(getApplicationContext());
        if(db.isLogIn()){
            Intent intent = new Intent(this,HomeActivity.class);
            intent.putExtra("userId",db.getUserId());
            startActivity(intent);
        }
    }
}
