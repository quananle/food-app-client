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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leanhquan.deliveryfoodver2.Model.User;

public class RegisterActivity extends AppCompatActivity {
    private ImageView       imgBack;
    private Button          btnSignup;
    private EditText        edtPhone, edtUsername, edtPassword;
    private RelativeLayout  layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);

        layout.getLayoutParams().height = displayMetrics.heightPixels-750;

        final ScrollView mainscroll = findViewById(R.id.mainScroll);
        mainscroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mainscroll.post(new Runnable() {
                    @Override
                    public void run() {
                        mainscroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePhone() | !validatePassword() | !validateName()) {
                    Toast.makeText(RegisterActivity.this, "input wrong type", Toast.LENGTH_SHORT).show();
                }else  {
                    Register();
                }
            }
        });
    }

    private boolean validatePhone() {
        String valuephone = edtPhone.getText().toString();

        if (valuephone.isEmpty()) {
            edtPhone.setError("Cannot be empty");
            return false;
        } else if(valuephone.length()<9) {
            edtPhone.setError("Phone not exits!");
            return  false;
        } else {
            edtPhone.setError(null);
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

    private boolean validateName(){
        String valueName = edtUsername.getText().toString();
        if (valueName.isEmpty()) {
            edtUsername.setError("Cannot be empty");
            return false;
        } else {
            edtPassword.setError(null);
            return true;
        }
    }
    

    private void Register() {
        final String phone = edtPhone.getText().toString().trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userDb = database.getReference("user");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please waiting ...");
        progressDialog.show();

        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                edtPhone.setError(null);
                if(snapshot.child(phone).exists()){
                    progressDialog.dismiss();
                    edtPhone.setError("Phone number Already register");
                }else{
                    User user = new User(edtUsername.getText().toString(), edtPassword.getText().toString());
                    userDb.child(phone).setValue(user);
                    Toast.makeText(RegisterActivity.this, "Sign up successfuly", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void init() {
        imgBack = findViewById(R.id.imgback);
        btnSignup = findViewById(R.id.btnSignup);
        edtPassword = findViewById(R.id.edtPasswordRegis);
        edtUsername = findViewById(R.id.edtUserNameRegis);
        edtPhone = findViewById(R.id.edtPhoneRegis);
        layout=findViewById(R.id.layoutRegis);
    }
}
