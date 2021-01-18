package com.leanhquan.deliveryfoodver2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leanhquan.deliveryfoodver2.Inteface.ItemClickListener;
import com.leanhquan.deliveryfoodver2.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickListener itemClickListener;
    public ImageView imgFood, quickCart;
    public TextView nameFood, priceFood;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        imgFood = itemView.findViewById(R.id.food_image);
        nameFood = itemView.findViewById(R.id.food_name);
        quickCart = itemView.findViewById(R.id.btn_quick_cart);
        priceFood = itemView.findViewById(R.id.food_price);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
