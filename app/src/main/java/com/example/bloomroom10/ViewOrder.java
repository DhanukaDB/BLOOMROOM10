// ViewOrder.java

package com.example.bloomroom10;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewOrder extends AppCompatActivity {

    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order);

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        orderAdapter = new OrderAdapter(this, orderList);
        recyclerViewOrders.setAdapter(orderAdapter);

        fetchOrders();
    }

    private void fetchOrders() {
        db.collection("orders")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        orderList.clear(); // Clear the existing list
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Order order = document.toObject(Order.class);
                            order.setId(document.getId());
                            orderList.add(order);
                        }
                        orderAdapter.notifyDataSetChanged();
                    } else {
                        // Handle errors
                    }
                });
    }
}
