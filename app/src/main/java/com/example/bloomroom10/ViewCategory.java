package com.example.bloomroom10;

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

public class ViewCategory extends AppCompatActivity {

    private RecyclerView recyclerViewCategories;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_category);

        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this));
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categoryList);
        recyclerViewCategories.setAdapter(categoryAdapter);

        db = FirebaseFirestore.getInstance();
        fetchCategories();

        // Set the click listeners for edit and delete in the adapter
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                // Handle edit click
                editCategory(position);
            }

            @Override
            public void onDeleteClick(int position) {
                // Handle delete click
                deleteCategory(position);
            }
        });
    }

    private void fetchCategories() {
        db.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Category category = document.toObject(Category.class);
                            category.setId(document.getId());
                            categoryList.add(category);
                        }
                        categoryAdapter.notifyDataSetChanged();
                    } else {
                        // Handle errors
                    }
                });
    }

    // Open the edit dialog when the "Edit" button is clicked
    public void editCategory(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Category");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_category, null);
        builder.setView(viewInflated);

        final EditText editTextName = viewInflated.findViewById(R.id.editTextName);
        final EditText editTextDescription = viewInflated.findViewById(R.id.editTextDescription);

        Category category = categoryList.get(position);
        editTextName.setText(category.getName());
        editTextDescription.setText(category.getDescription());

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = editTextName.getText().toString();
            String newDescription = editTextDescription.getText().toString();

            // Update the category in the list
            Category updatedCategory = new Category(newName, newDescription);
            updatedCategory.setId(category.getId());
            categoryList.set(position, updatedCategory);
            categoryAdapter.notifyItemChanged(position);

            // Update the category in Firestore
            db.collection("categories")
                    .document(category.getId())
                    .set(updatedCategory);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Delete a category at the specified position
    private void deleteCategory(int position) {
        Category categoryToDelete = categoryList.get(position);

        // Remove the category from the list
        categoryList.remove(position);
        categoryAdapter.notifyItemRemoved(position);

        // Delete the category from Firestore
        db.collection("categories")
                .document(categoryToDelete.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Handle successful deletion
                    Toast.makeText(ViewCategory.this, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle deletion failure
                    Toast.makeText(ViewCategory.this, "Failed to delete category", Toast.LENGTH_SHORT).show();
                    // If deletion fails, you might want to add the category back to the list
                    categoryList.add(position, categoryToDelete);
                    categoryAdapter.notifyItemInserted(position);
                });
    }
}
