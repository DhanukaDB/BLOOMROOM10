package com.example.bloomroom10;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateCategoryActivity extends AppCompatActivity {

    private EditText editTextCategoryName;
    private EditText editTextCategoryDescription;
    private Button buttonCreateCategory;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        editTextCategoryDescription = findViewById(R.id.editTextCategoryDescription);
        buttonCreateCategory = findViewById(R.id.buttonCreateCategory);

        buttonCreateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCategory();
            }
        });
    }

    private void createCategory() {
        String categoryName = editTextCategoryName.getText().toString().trim();
        String categoryDescription = editTextCategoryDescription.getText().toString().trim();

        if (!categoryName.isEmpty() && !categoryDescription.isEmpty()) {
            // Create a new category document in the "categories" collection
            Category category = new Category(categoryName, categoryDescription);

            db.collection("categories")
                    .document(categoryName) // Assuming categoryName is unique
                    .set(category)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CreateCategoryActivity.this, "Category created successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(CreateCategoryActivity.this, "Failed to create category", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
