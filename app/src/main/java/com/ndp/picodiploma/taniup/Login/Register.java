package com.ndp.picodiploma.taniup.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.ndp.picodiploma.taniup.MainActivity;
import com.ndp.picodiploma.taniup.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        progressDialog();


        binding.tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //need attention probably error
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() == true) {
                    register(binding.etUsername.getText().toString(),
                            binding.etEmail.getText().toString(),
//                            binding.etPhone.getText().toString(),
                            binding.etPassword.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Logic Register
    private void register(String name, String email, String password) {
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful() && task.getResult() != null) {
                        FirebaseUser user = task.getResult().getUser();
                        if (user!=null) {
                            UserProfileChangeRequest add = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(add).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    reload();
                                }
                            });
                        }else  {
                            Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), Login.class));
        Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    //need attention probably error
    private boolean validate() {

        String emailInput = binding.etEmail.getText().toString();

        if (!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()
                && binding.etPassword.getText().length() >= 6
                && binding.etRePassword.getText().length() >= 6
                && binding.etUsername .getText().length() > 0) {
                if (binding.etPassword.getText().toString().equals(binding.etRePassword.getText().toString())) {
                    return true;
                }else {
                    Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                }
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    //need attention probably error
    private void progressDialog () {
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
    }


}