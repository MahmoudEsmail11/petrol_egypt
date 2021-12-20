package com.example.petrolegypt.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.derohimat.sweetalertdialog.SweetAlertDialog;
import com.example.petrolegypt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class registration extends AppCompatActivity {

    LinearLayout backbutton;
    EditText name,password,confirmPassword,email;
    Button save;
    private FirebaseAuth mAuth ;
    private FirebaseFirestore db;
    SweetAlertDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reigister);

        backbutton = findViewById(R.id.btnback);
        name = findViewById(R.id.edtnamerig);
        password = findViewById(R.id.edtpasswordrig);
        confirmPassword = findViewById(R.id.edtconfirmpasswordrig);
        email = findViewById(R.id.edtemailrig);
        save = findViewById(R.id.btnsavenewaccount);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        save.setOnClickListener(v -> check());
        backbutton.setOnClickListener(v -> {
            Intent i = new Intent(registration.this, LoginActivity.class);
            startActivity(i);
        });






    }

    private void check() {
        String username = name.getText().toString();
        String pass = password.getText().toString();
        String confirmPass = confirmPassword.getText().toString();
        String Email = email.getText().toString();


        if (username.isEmpty()){
            name.requestFocus();
            name.setError("please enter name ");
        }

        else if (Email.isEmpty()){
            email.requestFocus();
            email.setError("please enter email");
        }
         else if (pass.isEmpty()){
            password.requestFocus();
            password.setError("please enter password ");
        }
        else if (confirmPass.isEmpty()){
            confirmPassword.requestFocus();
            confirmPassword.setError("please enter confirmPassword");
        }

         else if(!confirmPass.equals(pass)){


            confirmPassword.requestFocus();
            confirmPassword.setError("confirmPassword not equals");
        }
         else{
            signinfirebase(Email,pass);
             pDialog = new SweetAlertDialog(registration.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();

        }

    }

    private void signinfirebase(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            saveUserDate();
                            pDialog.dismiss();

                            new SweetAlertDialog(registration.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Good job!")
                                    .setContentText("account created")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Intent j = new Intent(registration.this,LoginActivity.class);
                                            startActivity(j);
                                        }
                                    })
                                    .show();


                        } else {
                            Toast.makeText(registration.this, "Exception : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
    private void saveUserDate() {
        String uID =mAuth.getCurrentUser().getUid();
        Map<String, Object> user = new HashMap<>();
        user.put("id", uID);
        user.put("username", name.getText().toString().trim());
        user.put("password", password.getText().toString().trim());
        user.put("email", email.getText().toString().trim());
        db.collection("user")
                .document(uID)
                .set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(registration.this, "account Created Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(registration.this, "Error + \\n" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}