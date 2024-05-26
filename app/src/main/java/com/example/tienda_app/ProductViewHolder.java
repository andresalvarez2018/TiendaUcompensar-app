package com.example.tienda_app;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public TextView textViewDescription;
    public TextView textViewPrice;
    public ImageView imageViewThumbnail;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewDescription = itemView.findViewById(R.id.textViewDescription);
        textViewPrice = itemView.findViewById(R.id.textViewPrice);
        imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
    }
}