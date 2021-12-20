package com.example.petrolegypt.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.derohimat.sweetalertdialog.SweetAlertDialog;
import com.example.petrolegypt.MainActivity2;
import com.example.petrolegypt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    TextView forgetpassword, rigister;
    Button login;
    ImageView google, fb;
    FirebaseAuth firebaseAuth;
    SweetAlertDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.edtemaillogin);
        password = findViewById(R.id.edtpasswordlogin);
        forgetpassword = findViewById(R.id.tvxforgetpassword);

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  Intent AA =  new Intent(LoginActivity.this, Forgetpassword.class);
  startActivity(AA);

            }

        });
        login = findViewById(R.id.btnlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check();


            }
        });

        google = findViewById(R.id.imggooglelogin);
        fb = findViewById(R.id.imgfblogin);

        rigister = findViewById(R.id.tvxreg);
        rigister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent mahmoud = new Intent(LoginActivity.this, registration.class);
                startActivity(mahmoud);
            }

        });


    }

    private void check() {
        String Email = email.getText().toString();
        String Pass = password.getText().toString();

        if (Email.isEmpty()){
            email.requestFocus();
            email.setError("please enter email");
        }
            else if (Pass.isEmpty()){
                password.requestFocus();
                password.setError("please enter password");
        }
            else {

               signInFirebase(Email,Pass);

             pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();


        }





    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean login = getSharedPreferences("Login",MODE_PRIVATE).getBoolean("islogin",false);
        if (login==true){
            goToHome();
        }


    }

    private void signInFirebase(String email, String pass) {
            firebaseAuth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                        getSharedPreferences("Login",MODE_PRIVATE).edit().putBoolean("islogin",true).apply();
                                goToHome();

                            }else {
                                pDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });









    }
    private void goToHome(){
        Intent mm = new Intent(LoginActivity.this, MainActivity2.class);
        startActivity(mm);
    }

}