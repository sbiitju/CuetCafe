package com.example.cuetcafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String e,p;
    String user;
    TextView textView;

    public static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        final EditText email,pass;
        Button signin,signup;
        textView=findViewById(R.id.textviewreset);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                View view=getLayoutInflater().inflate(R.layout.resetpassword,null);
                final EditText editText;
                Button button;
                editText=view.findViewById(R.id.resetemail);
                button=view.findViewById(R.id.resetbutton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage("Sending a reset link.....");
                        progressDialog.show();
                        boolean state=isNetworkAvailable();
                        if(state==true){
                            firebaseAuth.sendPasswordResetEmail(editText.getText().toString());
                            Toast.makeText(LoginActivity.this, "Please Check your email and Reset password ", Toast.LENGTH_LONG).show();
                            AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Please check your mail and reset your password...");

                            builder.show();
                            editText.setText("");
                            progressDialog.hide();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                            finish();
                        }

                    }
                });
                builder.setView(view).show();
            }
        });
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        signin=findViewById(R.id.signin);
        signup=findViewById(R.id.signup);
        firebaseAuth= FirebaseAuth.getInstance();
        //firebaseAuth.getCurrentUser().isEmailVerified()
        if(firebaseAuth.getCurrentUser()!=null ){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        else {
            signup.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                @Override
                public void onClick(View v) {
                    e=email.getText().toString();
                    p=pass.getText().toString();
                    if(e.length()!=0){
                        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
                        dialog.setMessage("Creating an Account..");
                        dialog.show();
                        firebaseAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(LoginActivity.this, "a verification link is sent to your email,Please Check it and Verify..", Toast.LENGTH_LONG).show();
                                            dialog.hide();
                                            Toast.makeText(LoginActivity.this, "After Verification..please Sign in", Toast.LENGTH_LONG).show();
                                        }
                                    });
//                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Faield!!", Toast.LENGTH_SHORT).show();
                                    dialog.hide();
                                }
                            }
                        });

                    }
                    else{
                        email.setError("Invalid!!");
                        pass.setError("Input Here!!");
                    }
                }
            });
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    e=email.getText().toString();
                    p=pass.getText().toString();
                    if(e.length()!=0){
                        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
                        dialog.setMessage("Verifying to Sign In..");
                        dialog.show();
                        firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()&&firebaseAuth.getCurrentUser().isEmailVerified()){
                                    Toast.makeText(LoginActivity.this, "Successfully LogIn!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Your Email is not Verified!! Try Again!!", Toast.LENGTH_SHORT).show();
                                    dialog.hide();
                                }

                            }
                        });
                    }
                    else{
                        pass.setError("Input Here!!");
                        email.setError("Invalid!!");

                    }
                }
            });
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
