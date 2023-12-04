package com.example.bloomroom10;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;


public class CreateFlowerActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

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

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



    }

    private void openGallery() {
        // Create an Intent to pick an image from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            Uri imageUri = data.getData();

            // Display the selected image
            imageViewSelectedImage.setImageURI(imageUri);

            // You can now upload the image to Firebase Storage
            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        // TODO: Implement Firebase Storage upload logic here

        // Get a reference to the Firebase Storage location
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + System.currentTimeMillis() + ".jpg");

        // Upload the file to Firebase Storage
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully
                        // Now, you can get the download URL for the image
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                // Now you have the download URL, you can save it to Firebase Firestore or use it as needed
                                String imageUrl = downloadUri.toString();
                                // Save the URL to Firestore or use it in any other way
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(CreateFlowerActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
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
