package com.example.bloomroom10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    TextView gotoLogin;
    EditText name_0, emailAddress_0, password_0;
    Button register_0;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        gotoLogin = findViewById(R.id.TXT_link2);
        register_0 = findViewById(R.id.BTN_register);
        name_0 = findViewById(R.id.TXT_name);
        emailAddress_0 = findViewById(R.id.TXT_Remail);
        password_0 = findViewById(R.id.TXT_Rpassword);

        register_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(name_0);
                checkField(emailAddress_0);
                checkField(password_0);

                if (valid){
                    fAuth.createUserWithEmailAndPassword(emailAddress_0.getText().toString(), password_0.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Register.this,"Account is Created Successfully", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("User").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("Name",name_0.getText().toString());
                            userInfo.put("UserEmail",emailAddress_0.getText().toString());
                            userInfo.put("isUser","1");

                            df.set(userInfo);

                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this,"Failed to Created Account", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(goLogin);
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