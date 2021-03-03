package com.example.cuetcafe;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartViewHolder extends RecyclerView.ViewHolder {
    TextView name,amount,price;
    FloatingActionButton button;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.cartname);
        amount=itemView.findViewById(R.id.cartamount);
        price=itemView.findViewById(R.id.carttaka);
        button=itemView.findViewById(R.id.cartdelet);
    }
}
