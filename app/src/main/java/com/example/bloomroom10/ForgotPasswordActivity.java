package com.example.bloomroom10;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button back, retrieve;
    Spinner spinner;
    TextView question, password;
    EditText answer, email;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        back = findViewById(R.id.button2);
        spinner = findViewById(R.id.spinner);
        question = findViewById(R.id.TXT_empty);
        password = findViewById(R.id.edit_pass);
        answer = findViewById(R.id.answer);
        email = findViewById(R.id.Forgot_email);
        retrieve = findViewById(R.id.getPass);

        retrieve.setBackgroundColor(Color.BLUE);
        back.setBackgroundColor(Color.BLUE);

        spinner.setOnItemSelectedListener(this);

        back.setOnClickListener(v -> {
            finish();
        });
        retrieve.setOnClickListener(v -> {
            String user = email.getText().toString();
            String ans = answer.getText().toString();
            String secQue = question.getText().toString();

           /* if (user.equals("") || ans.equals("")) {
                Toast.makeText(ForgotPasswordActivity.this, "You are missing username/answer field", Toast.LENGTH_SHORT).show();
            } else {
                Cursor cursor = fStore.viewPassword(user, ans, secQue);

                if (cursor.getCount() != 0) {
                    Toast.makeText(ForgotPasswordActivity.this, "Username and Answer are matched", Toast.LENGTH_SHORT).show();

                    cursor.moveToNext();
                    password.setText(cursor.getString(1));
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid Username or Answer or Security Question. Try Again!", Toast.LENGTH_SHORT).show();
                }
            }*/
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();
        question.setText(choice);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}