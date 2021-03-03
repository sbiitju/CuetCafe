package com.example.cuetcafe;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView name,description,price;
    ImageView foodpic;
    FloatingActionButton button;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.foodname);
        description=itemView.findViewById(R.id.description);
        price=itemView.findViewById(R.id.price);
        foodpic=itemView.findViewById(R.id.foodimage);
        button=itemView.findViewById(R.id.button);
    }
}
