package com.example.classyaccessories.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classyaccessories.Interface.ItemClickListner;
import com.example.classyaccessories.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductDescription,TxtProductPrice;
    public ImageView imageView;
    public ItemClickListner itemClickListner;
    private ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        TxtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
    listner.onClick(view, getAdapterPosition(),false);
    }
}
