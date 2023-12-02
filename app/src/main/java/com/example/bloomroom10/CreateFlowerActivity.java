package com.example.bloomroom10;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CreateFlowerActivity extends AppCompatActivity {

    private EditText editTextFlowerName;
    private EditText editTextFlowerDescription;
    private EditText editTextFlowerPrice;
    private Spinner spinnerFlowerCategory;
    private EditText editTextFlowerImageUrl;
    private ImageView imageViewSelectedImage;
    private Button buttonSelectImage;
    private Spinner spinnerOfferPercentage;
    private Button buttonCreateFlower;

    private FirebaseFirestore db;
    private List<String> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flower);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        editTextFlowerName = findViewById(R.id.editTextFlowerName);
        editTextFlowerDescription = findViewById(R.id.editTextFlowerDescription);
        editTextFlowerPrice = findViewById(R.id.editTextFlowerPrice);
        spinnerFlowerCategory = findViewById(R.id.spinnerCategory);
        editTextFlowerImageUrl = findViewById(R.id.editTextFlowerImageUrl);
        imageViewSelectedImage = findViewById(R.id.imageViewSelectedImage);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        spinnerOfferPercentage = findViewById(R.id.spinnerOfferPercentage);
        buttonCreateFlower = findViewById(R.id.buttonCreateFlower);

        categoryList = new ArrayList<>();

        // Fetch categories from Firestore
        db.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String categoryName = document.toObject(Category.class).getName();
                                categoryList.add(categoryName);
                            }

                            // Populate spinner with categories
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateFlowerActivity.this, android.R.layout.simple_spinner_item, categoryList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerFlowerCategory.setAdapter(adapter);
                        } else {
                            // Handle failure
                            Toast.makeText(CreateFlowerActivity.this, "Failed to fetch categories", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        buttonCreateFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFlower();
            }
        });
    }

    private void createFlower() {
        String flowerName = editTextFlowerName.getText().toString().trim();
        String flowerDescription = editTextFlowerDescription.getText().toString().trim();
        String flowerPrice = editTextFlowerPrice.getText().toString().trim();
        String flowerCategory = spinnerFlowerCategory.getSelectedItem().toString().trim();
        String flowerImageUrl = editTextFlowerImageUrl.getText().toString().trim();
        String offerPercentage = spinnerOfferPercentage.getSelectedItem().toString();

        if (!flowerName.isEmpty() && !flowerDescription.isEmpty() && !flowerPrice.isEmpty() && !flowerCategory.isEmpty() && !flowerImageUrl.isEmpty()) {
            // Create a new flower document in the "flowers" collection
            Flower flower = new Flower(flowerName, flowerDescription, Double.parseDouble(flowerPrice), flowerCategory, flowerImageUrl, offerPercentage);

            db.collection("flowers")
                    .document(flowerName) // Assuming flowerName is unique
                    .set(flower)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CreateFlowerActivity.this, "Flower created successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(CreateFlowerActivity.this, "Failed to create flower", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
