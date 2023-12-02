package com.example.bloomroom10;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ViewFlower extends AppCompatActivity {

    private RecyclerView recyclerViewFlowers;
    private FlowerAdapter flowerAdapter;
    private List<Flower> flowerList;
    private FirebaseFirestore db;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_flower);

        recyclerViewFlowers = findViewById(R.id.recyclerViewFlowers);
        recyclerViewFlowers.setLayoutManager(new LinearLayoutManager(this));
        flowerList = new ArrayList<>();
        flowerAdapter = new FlowerAdapter(this, flowerList);
        recyclerViewFlowers.setAdapter(flowerAdapter);

        db = FirebaseFirestore.getInstance();

        // Check if an intent with category extra is received
        if (getIntent().hasExtra("category")) {
            selectedCategory = getIntent().getStringExtra("category");
            fetchFlowersByCategory(selectedCategory);
        } else {
            fetchFlowers(); // Fetch all flowers if no specific category is selected
        }

        // Set the click listeners for edit and delete in the adapter
        flowerAdapter.setOnItemClickListener(new FlowerAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                // Handle edit click
                editFlower(position);
            }

            @Override
            public void onDeleteClick(int position) {
                // Handle delete click
                deleteFlower(position);
            }

            @Override
            public void onCategoryClick(int position) {
                // Handle category click
                navigateToCategorizedFlowers(position);
            }
        });
    }

    private void fetchFlowersByCategory(String category) {
        db.collection("flowers")
                .whereEqualTo("flowerCategory", category)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        flowerList.clear(); // Clear the existing list
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Flower flower = document.toObject(Flower.class);
                            flower.setId(document.getId());
                            flowerList.add(flower);
                        }
                        flowerAdapter.notifyDataSetChanged();
                    } else {
                        // Handle errors
                        Toast.makeText(ViewFlower.this, "Failed to fetch flowers", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToCategorizedFlowers(int position) {
        Flower selectedFlower = flowerList.get(position);

        if (selectedFlower != null) {
            String category = selectedFlower.getFlowerCategory();
            // Call the method to filter and display flowers based on the selected category
            filterFlowersByCategory(category);
        }
    }

    private void fetchFlowers() {
        db.collection("flowers")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Flower flower = document.toObject(Flower.class);
                            flower.setId(document.getId());
                            flowerList.add(flower);
                        }
                        flowerAdapter.notifyDataSetChanged();
                    } else {
                        // Handle errors
                    }
                });
    }

    // Open the edit dialog when the "Edit" button is clicked
    public void editFlower(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Flower");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_flower, null);
        builder.setView(viewInflated);

        final EditText editTextName = viewInflated.findViewById(R.id.editTextName);
        final EditText editTextDescription = viewInflated.findViewById(R.id.editTextDescription);
        // Add other fields as needed

        Flower flower = flowerList.get(position);
        editTextName.setText(flower.getFlowerName());
        editTextDescription.setText(flower.getFlowerDescription());
        // Set other fields

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = editTextName.getText().toString();
            String newDescription = editTextDescription.getText().toString();
            // Get other field values

            if (!newName.isEmpty() && !newDescription.isEmpty()) {
                // Update the flower in the list
                Flower updatedFlower = new Flower(
                        newName,
                        newDescription,
                        flower.getFlowerPrice(),
                        flower.getFlowerCategory(),
                        flower.getFlowerImageUrl(),
                        flower.getOfferPercentage()
                );
                updatedFlower.setId(flower.getId());
                flowerList.set(position, updatedFlower);
                flowerAdapter.notifyItemRangeChanged(position, flowerList.size());

                // Update the flower in Firestore
                db.collection("flowers")
                        .document(flower.getId())
                        .set(updatedFlower);
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Delete a flower at the specified position
    private void deleteFlower(int position) {
        Flower flowerToDelete = flowerList.get(position);

        // Remove the flower from the list
        flowerList.remove(position);
        flowerAdapter.notifyItemRemoved(position);

        // Delete the flower from Firestore
        db.collection("flowers")
                .document(flowerToDelete.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Handle successful deletion
                    Toast.makeText(ViewFlower.this, "Flower deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle deletion failure
                    Toast.makeText(ViewFlower.this, "Failed to delete flower", Toast.LENGTH_SHORT).show();
                    // If deletion fails, you might want to add the flower back to the list
                    flowerList.add(position, flowerToDelete);
                    flowerAdapter.notifyItemInserted(position);
                });
    }

    private void filterFlowersByCategory(String category) {
        List<Flower> filteredList = new ArrayList<>();

        for (Flower flower : flowerList) {
            if (flower.getFlowerCategory().equals(category)) {
                filteredList.add(flower);
            }
        }

        flowerAdapter.setFlowerList(filteredList);
        flowerAdapter.notifyDataSetChanged();
    }

}
