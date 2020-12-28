package com.leanhquan.deliveryfoodver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leanhquan.deliveryfoodver2.Model.User;

public class LoginActivity extends AppCompatActivity {
    private EditText       edtPhonenumber, edtPassword;
    private Button         btnLogin;
    private TextView       txtSignUp, txtFogotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Go to Sign Up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        txtFogotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Go to reset pass", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validatePhone() {
        String valuephone = edtPhonenumber.getText().toString();

        if (valuephone.isEmpty()) {
            edtPhonenumber.setError("Cannot be empty");
            return false;
        } else if(valuephone.length()<9) {
            edtPhonenumber.setError("Phone not exits!");
            return  false;
        } else {
            edtPhonenumber.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String valuepass = edtPassword.getText().toString();
        if (valuepass.isEmpty()) {
           edtPassword.setError("Cannot be empty");
            return false;
        } else {
           edtPassword.setError(null);
            return true;
        }
    }

    public void loginUser(){
        if (!validatePhone() | !validatePassword()) {
            Toast.makeText(this, "input wrong type", Toast.LENGTH_SHORT).show();
        }else  {
            isUser();
        }
    }

    private void isUser() {
        final String userEntered = edtPhonenumber.getText().toString().trim();
        final String passwordEntered = edtPassword.getText().toString().trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference user = database.getReference("user");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please waiting ...");
        progressDialog.show();

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    edtPhonenumber.setError(null);
                    User user = dataSnapshot.child(userEntered).getValue(User.class);
                    if (user.getPassword().equals(passwordEntered)) {
                        edtPhonenumber.setError(null);
                        Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        progressDialog.dismiss();
                        edtPassword.setError("Wrong Password");
                        edtPassword.requestFocus();
                    }
                }else {
                    progressDialog.dismiss();
                    edtPhonenumber.setError("Phone does not exits");
                    edtPassword.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }

    private void initView() {
        edtPassword = findViewById(R.id.edtPassword);
        edtPhonenumber = findViewById(R.id.edtPhonenumber);
        btnLogin = findViewById(R.id.btnLogin);
        txtFogotPass = findViewById(R.id.txtFogotPass);
        txtSignUp = findViewById(R.id.txtSignUp);
    }
}
