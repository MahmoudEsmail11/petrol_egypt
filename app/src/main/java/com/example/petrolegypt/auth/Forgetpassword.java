package com.example.petrolegypt.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.petrolegypt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpassword extends AppCompatActivity {

            LinearLayout back;
            EditText forgtemail;


            Button sendemail;
            FirebaseAuth firebaseAuth;
            private String mahmoud;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        firebaseAuth = FirebaseAuth. getInstance();

        back = findViewById(R.id.back);
        forgtemail = findViewById(R.id.edtforgtemail);
        sendemail = findViewById(R.id.btnsendemail);

        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();

            }
        });



    }

    private void check() {
         String Email = forgtemail.getText().toString();
        if (Email.isEmpty()) {
            forgtemail.requestFocus();
            forgtemail.setError("please enter email");

        }
           else  {

              checkEmailInFirebase(Email);


        }

    }

        private void checkEmailInFirebase(String email){
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Forgetpassword.this, "done", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Forgetpassword.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                    }
                });

        }


        }