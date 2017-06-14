package com.example.r3l0ad3d.bazarbuy;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.r3l0ad3d.bazarbuy.ModelClass.LocalDataSave;
import com.example.r3l0ad3d.bazarbuy.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private FirebaseAuth auth;

    final static String ERROR = "Empty Field Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
    }

    public void signIn(View view) {
        if(binding.tvEmailSignIn.getText().equals("")){
            binding.tvEmailSignIn.setError(ERROR);
        }else if(binding.tvPasswordSignIn.getText().equals("")){
            binding.tvPasswordSignIn.setError(ERROR);
        }else {
            auth.signInWithEmailAndPassword(binding.tvEmailSignIn.getText().toString(),binding.tvPasswordSignIn.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String userId = task.getResult().getUser().getUid();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        //intent.putExtra("userId",userId);
                        LocalDataSave db = new LocalDataSave(getApplicationContext(),userId);
                        db.dataSave();
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Log in Success",Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
