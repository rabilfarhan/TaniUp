package com.ndp.picodiploma.taniup.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ndp.picodiploma.taniup.MainActivity;
import com.ndp.picodiploma.taniup.R;

public class Login extends AppCompatActivity {

    private Button btnLogin, btnSignGoogle;
    private EditText etEmail, etPassword;
    private TextView tvSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        varWidget();
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        progressDialog();

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        //need attention probably error
        btnLogin.setOnClickListener(View -> {
            if (validate() == false) {
                Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_SHORT).show();
            }else {
                login(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });

    }

    //Logic Login
    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!= null) {
                    if (task.getResult().getUser()!= null) {
                        reload();
                    }else {
                        Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }else   {
                    Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void varWidget() {
        tvSignUp = findViewById(R.id.tvSignUp);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignGoogle = findViewById(R.id.btnSignInWithGo);
    }

    //need attention probably error
    private boolean validate() {
        String emailInput = etEmail.getText().toString();
        if (etEmail.getText().length() > 0 && etPassword.getText().length() > 0 && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String name = firebaseUser.getDisplayName();
        Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_LONG).show();
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    //need attention probably error
    private void progressDialog () {
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
    }

}