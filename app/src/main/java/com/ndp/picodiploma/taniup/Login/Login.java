package com.ndp.picodiploma.taniup.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ndp.picodiploma.taniup.MainActivity;
import com.ndp.picodiploma.taniup.R;

public class Login extends AppCompatActivity {

    private Button btnLogin, btnSignGoogle;
    private EditText etEmail, etPassword;
    private TextView tvSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    String TAG = "GOOGLE SIGN IN";

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

        btnSignGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSignIn();
            }
        });

        //Logic login With Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1007488134477-b7p9s1nnfirgkb5k64agf6ud3ae5h2rp.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    //Logic Login With Email And Password
    private void login(String email, String password) {
        progressDialog.show();
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

    private void goSignIn () {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Sign in Was Successfull auth firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG,"firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                //Sign in failed, Update UI properly
                Log.w(TAG, "Google Sign In Failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Sign in success, update UI with the Signed User information
                            Log.d(TAG, "SignInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        }else {
                            Log.w(TAG, "Google Sign In Failed", task.getException());
                        }
                        reload();
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