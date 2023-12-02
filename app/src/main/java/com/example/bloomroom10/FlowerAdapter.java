package com.example.bloomroom10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder> {

    private List<Flower> flowers;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public FlowerAdapter(Context context, List<Flower> flowers) {
        this.context = context;
        this.flowers = flowers;
    }

    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flower, parent, false);
        return new FlowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {
        Flower flower = flowers.get(position);
        holder.textViewFlowerName.setText(flower.getFlowerName());
        holder.textViewFlowerDescription.setText(flower.getFlowerDescription());
        holder.textViewFlowerPrice.setText(String.valueOf((int) flower.getFlowerPrice()));
//        holder.textViewFlowerOffer.setText(flower.getOfferPercentage());
//        holder.imageViewFlowerImage.setText(flower.getFlowerImageUrl());
        // Add other fields as needed

        // Set click listeners for edit and delete buttons
        holder.buttonEdit.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onEditClick(position);
            }
        });

        holder.buttonDelete.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flowers.size();
    }

    public static class FlowerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFlowerName;
        TextView textViewFlowerDescription;

        TextView textViewFlowerPrice;

        TextView textViewFlowerOffer;

        ImageView imageViewFlowerImage;
        Button buttonEdit;
        Button buttonDelete;

        public FlowerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFlowerName = itemView.findViewById(R.id.textViewFlowerName);
            textViewFlowerDescription = itemView.findViewById(R.id.textViewFlowerDescription);
            textViewFlowerPrice = itemView.findViewById(R.id.textViewFlowerPrice);  // Add this line
            textViewFlowerOffer = itemView.findViewById(R.id.textViewFlowerOffer);  // Add this line
            imageViewFlowerImage = itemView.findViewById(R.id.imageViewFlowerImage);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
