package com.example.r3l0ad3d.bazarbuy;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.r3l0ad3d.bazarbuy.ModelClass.LocalDataSave;
import com.example.r3l0ad3d.bazarbuy.ModelClass.UserInformation;
import com.example.r3l0ad3d.bazarbuy.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    final static String ERROR = "Empty Field Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("root").child("user");
        auth = FirebaseAuth.getInstance();

    }

    public void signUp(View view) {

        final String userName = binding.etFUllNameSignUp.getText().toString().trim();
        final String userEmail = binding.etEmailIdSignUp.getText().toString().trim();
        final String userMobileNo = binding.etMobileNoSignUp.getText().toString().trim();
        final String userLocation = binding.etLocationSignUp.getText().toString().trim();
        final String userPass = binding.etPasswordSignUp.getText().toString().trim();
        String confPass = binding.etConfirmPasswordSignUp.getText().toString().trim();

        if(userName.equals("")){
            binding.etFUllNameSignUp.setError(ERROR);
        }else if(userEmail.equals("")){
            binding.etEmailIdSignUp.setError(ERROR);
        }else if(userMobileNo.equals("")){
            binding.etMobileNoSignUp.setError(ERROR);
        }else if(userLocation.equals("")){
            binding.etLocationSignUp.setError(ERROR);
        }else if(userPass.equals("")){
            binding.etPasswordSignUp.setError(ERROR);
        }else if(!(userPass.equals(confPass))){
            binding.etConfirmPasswordSignUp.setError("Password Not Match");
        }else {
            auth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String id = task.getResult().getUser().getUid();
                        UserInformation uInfo = new UserInformation();
                        uInfo.setUserId(id);
                        uInfo.setUserEmail(userEmail);
                        uInfo.setUserFullName(userName);
                        uInfo.setUserLocation(userLocation);
                        uInfo.setUserMobileNo(userMobileNo);
                        uInfo.setUserPassword(userPass);
                        uInfo.setLogIn(true);

                        LocalDataSave db = new LocalDataSave(getApplicationContext(),id);
                        db.dataSave();
                        db.setData(uInfo);

                        reference.child(id).setValue(uInfo);
                        Toast.makeText(getApplicationContext(),"Sign up Success",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Something Error",Toast.LENGTH_LONG).show();
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
