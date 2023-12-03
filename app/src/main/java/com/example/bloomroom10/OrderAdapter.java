// OrderAdapter.java

package com.example.bloomroom10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;
    private Context context;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.textViewOrderFlowerName.setText(order.getOrderFlowerName());
        holder.textViewOrderFlowerOffer.setText(order.getOrderFlowerOffer());
        holder.textViewCustomerName.setText(order.getCustomerName());
        holder.textViewContactNumber.setText(String.valueOf(order.getContactNumber()));
        holder.textViewCustomerAddress.setText(order.getCustomerAddress());
        holder.textViewOrderStatus.setText(order.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderFlowerName;
        TextView textViewOrderFlowerOffer;
        TextView textViewCustomerName;
        TextView textViewContactNumber;
        TextView textViewCustomerAddress;
        TextView textViewOrderStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderFlowerName = itemView.findViewById(R.id.textViewOrderFlowerName);
            textViewOrderFlowerOffer = itemView.findViewById(R.id.textViewOrderFlowerOffer);
            textViewCustomerName = itemView.findViewById(R.id.textViewCustomerName);
            textViewContactNumber = itemView.findViewById(R.id.textViewContactNumber);
            textViewCustomerAddress = itemView.findViewById(R.id.textViewCustomerAddress);
            textViewOrderStatus = itemView.findViewById(R.id.textViewOrderStatus);
        }
    }
}
