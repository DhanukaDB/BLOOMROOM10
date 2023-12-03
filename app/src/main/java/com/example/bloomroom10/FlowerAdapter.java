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
import com.bumptech.glide.Glide;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder> {

    private List<Flower> flowers;
    private Context context;
    private OnItemClickListener mListener;
    private boolean isAdmin;





    public interface OnItemClickListener {
        void onEditClick(int position);

        void onDeleteClick(int position);

        void onCategoryClick(int position);

        void onOrderClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public FlowerAdapter(Context context, List<Flower> flowers) {
        this.context = context;
        this.flowers = flowers;
        this.isAdmin = false;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flower, parent, false);
        return new FlowerViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {
        Flower flower = flowers.get(position);
        holder.textViewFlowerName.setText(flower.getFlowerName());
        holder.textViewFlowerDescription.setText(flower.getFlowerDescription());
        holder.textViewFlowerPrice.setText(String.valueOf((int) flower.getFlowerPrice()));
        holder.textViewFlowerOffer.setText(flower.getOfferPercentage());
        holder.textViewFlowerCategory.setText(flower.getFlowerCategory());
        Glide.with(holder.imageViewFlowerImage.getContext())
                .load(flower.getFlowerImageUrl())
                .placeholder(R.drawable.images)
                //.circleCrop()
                .error(R.drawable.images)
                .into(holder.imageViewFlowerImage);
        // Set other fields as needed

        if (isAdmin) {
            holder.buttonEdit.setVisibility(View.VISIBLE);
            holder.buttonDelete.setVisibility(View.VISIBLE);
        } else {
            holder.buttonEdit.setVisibility(View.GONE);
            holder.buttonDelete.setVisibility(View.GONE);
        }

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

        holder.buttonOrder.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onOrderClick(position);
            }
        });


        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flowers.size();
    }

    public void setFlowerList(List<Flower> flowerList) {
        this.flowers = flowerList;
        notifyDataSetChanged();
    }

    public static class FlowerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFlowerName;
        TextView textViewFlowerDescription;
        TextView textViewFlowerPrice;
        TextView textViewFlowerOffer;
        TextView textViewFlowerCategory;
        ImageView imageViewFlowerImage;
        Button buttonEdit;
        Button buttonDelete;
        Button buttonOrder;
        Button buttonCategory;

        public FlowerViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textViewFlowerName = itemView.findViewById(R.id.textViewFlowerName);
            textViewFlowerDescription = itemView.findViewById(R.id.textViewFlowerDescription);
            textViewFlowerPrice = itemView.findViewById(R.id.textViewFlowerPrice);
            textViewFlowerCategory = itemView.findViewById(R.id.textViewFlowerCategory);
            textViewFlowerOffer = itemView.findViewById(R.id.textViewFlowerOffer);
            imageViewFlowerImage = itemView.findViewById(R.id.imageViewFlowerImage);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonOrder = itemView.findViewById(R.id.buttonOrder);

            buttonEdit.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(position);
                    }
                }
            });

            buttonDelete.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });

            buttonOrder.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onOrderClick(position);
                    }
                }
            });

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onCategoryClick(position);
                    }
                }
            });
        }
    }
}
