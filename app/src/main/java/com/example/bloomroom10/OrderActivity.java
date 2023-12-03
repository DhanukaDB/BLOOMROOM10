package com.example.bloomroom10;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderActivity extends AppCompatActivity {

    private TextView textViewFlowerName;
    private TextView textViewFlowerOffer;
    private EditText editTextCustomerName;
    private EditText editTextContactNumber;
    private EditText editTextCustomerAddress;
    private Button buttonPlaceOrder;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        textViewFlowerName = findViewById(R.id.textViewFlowerNameOrder);
        textViewFlowerOffer = findViewById(R.id.textViewFlowerOfferOrder);
        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextContactNumber = findViewById(R.id.editTextContactNumber);
        editTextCustomerAddress = findViewById(R.id.editTextCustomerAddress);
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

        db = FirebaseFirestore.getInstance();

        // Retrieve data from the intent
        String flowerName = getIntent().getStringExtra("flowerName");
        String flowerOffer = getIntent().getStringExtra("flowerOffer");

        // Set the data to the TextViews
        textViewFlowerName.setText("Flower Name: " + flowerName);
        textViewFlowerOffer.setText("Offer: " + flowerOffer);

        buttonPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void placeOrder() {
        // Get user input from EditText views
        String customerName = editTextCustomerName.getText().toString().trim();
        String contactNumberStr = editTextContactNumber.getText().toString().trim();
        String customerAddress = editTextCustomerAddress.getText().toString().trim();

        // Validate and convert contactNumber to int
        int contactNumber = 0;
        if (contactNumberStr != null && !contactNumberStr.isEmpty()) {
            try {
                contactNumber = Integer.parseInt(contactNumberStr);
            } catch (NumberFormatException e) {
                // Handle the case where contactNumberStr cannot be parsed to an integer
                Toast.makeText(this, "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                return;  // Return from the method to avoid further processing
            }
        }

        // Retrieve data from the intent
        String flowerName = getIntent().getStringExtra("flowerName");
        String flowerOffer = getIntent().getStringExtra("flowerOffer");

        // Check if required fields are not empty
        if (!customerName.isEmpty() && contactNumber != 0 && !customerAddress.isEmpty()) {
            // Create an Order object with the collected information
            Order order = new Order(
                    flowerName,
                    flowerOffer,
                    customerName,
                    contactNumber,
                    customerAddress,
                    "Pending" // Default status, you can modify it accordingly
            );

            db.collection("orders")
                    .document(customerName) // Assuming categoryName is unique
                    .set(order)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(OrderActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(OrderActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            // You might want to navigate back to the previous activity or perform other actions
            // after placing the order.

            // For example, finish the current activity:
            finish();
        } else {
            // Show an error message or handle the case where required fields are empty
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

}
