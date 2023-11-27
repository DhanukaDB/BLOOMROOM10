package com.example.bloomroom10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    TextView gotoRegister,forgotPass;
    EditText emailAddress_0, password_0;
    Button goLoginBTN;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gotoRegister = findViewById(R.id.TXT_link1);
        emailAddress_0 = findViewById(R.id.TXT_email);
        password_0 = findViewById(R.id.TXT_password);
        goLoginBTN = findViewById(R.id.BTN_login);
        forgotPass = findViewById(R.id.TXT_forgot);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        goLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(emailAddress_0);
                checkField(password_0);


                if (valid) {
                    String email = emailAddress_0.getText().toString();
                    String password = password_0.getText().toString();


                    if (isDefaultAdminLogin(email, password)) {
                        // Perform admin login
                        adminLogin();
                    } else {
                        // Perform regular user login
                        regularUserLogin(email, password);
                    }
                }
            }
        });
      gotoRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent goRegister = new Intent(getApplicationContext(), Register.class);
              startActivity(goRegister);
          }
      });

      forgotPass.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(Login.this, ForgotPasswordActivity.class);
              startActivity(intent);
          }
      });
    }
    private void regularUserLogin(String email, String password) {
        fAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
               Toast.makeText(Login.this,"Login is Successful", Toast.LENGTH_SHORT).show();
               checkUserAccessLevel(authResult.getUser().getUid());

               Intent intent = new Intent(Login.this, UserPage.class);
               startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void adminLogin() {
        Toast.makeText(Login.this, "Admin Login is Successful", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), AdminPage.class));
        finish();
    }
    private boolean isDefaultAdminLogin(String email, String password) {
        String defaultAdminEmail = "admin@gmail.com";
        String defaultAdminPassword = "admin123";

        return TextUtils.equals(email, defaultAdminEmail)&& TextUtils.equals(password, defaultAdminPassword);
    }
    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("User").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess" + documentSnapshot.getData());

                if (documentSnapshot.getString("is Admin") != null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });


    }

    public boolean checkField(EditText editTextValue) {
        if (editTextValue.getText().toString().isEmpty()){
            editTextValue.setError("Error");
            valid = false;
        }else {
            valid = true;
        }
        return valid;
    };

}