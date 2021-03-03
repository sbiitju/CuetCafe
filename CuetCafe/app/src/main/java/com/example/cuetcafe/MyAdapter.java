package com.example.cuetcafe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    ArrayList<Data> arrayList;
    public MyAdapter(Context context,ArrayList<Data> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.childview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.description.setText(arrayList.get(position).getDespcription());
        holder.price.setText(arrayList.get(position).getPrice());
        Picasso.get().load(arrayList.get(position).getUrl()).into(holder.foodpic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Check", Toast.LENGTH_SHORT).show();
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 if (arrayList.get(position).getInStock() == null) {
                                                     Toast.makeText(context, "Product Is not Available", Toast.LENGTH_SHORT).show();
                                                 } else {
                                                     AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                     View view1 = View.inflate(context, R.layout.add, null);
                                                     EditText amount;
                                                     TextView inStock;
                                                     amount = view1.findViewById(R.id.amount);
                                                     inStock = view1.findViewById(R.id.available);
                                                     inStock.setText("Available Food: "+arrayList.get(position).getInStock());
                                                     FloatingActionButton addtocart = view1.findViewById(R.id.addtocart);
                                                     addtocart.setOnClickListener(new View.OnClickListener() {
                                                         @RequiresApi(api = Build.VERSION_CODES.O)
                                                         @Override
                                                         public void onClick(View v) {
                                                             FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                                             String a = amount.getText().toString();
                                                             int number = Integer.valueOf(a);
                                                             if(number<=Integer.valueOf(arrayList.get(position).getInStock()))
                                                             {
                                                             String totalamount = String.valueOf(number * Integer.valueOf(arrayList.get(position).getPrice()));
                                                                 Cart cart = new Cart(arrayList.get(position).getName(), a, totalamount);

                                                                 FirebaseAuth firebaseAuth1=FirebaseAuth.getInstance();
                                                                 DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
                                                                 databaseReference.child(cart.getName()).setValue(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                     @Override
                                                                     public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        Toast.makeText(view1.getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                                                                    context.startActivity(new Intent(context,MainActivity.class));
                                                                    }
                                                                    else {
                                                                        Toast.makeText(view1.getContext(), "Failed", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                     }
                                                                 });






//                                                                 DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ÖrderList");
//                                                                 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
//                                                                 LocalDateTime now = LocalDateTime.now();
//                                                                 String s=dtf.format(now);
//                                                             databaseReference.child(s).child(firebaseAuth.getUid()).child(arrayList.get(position).getName()).setValue(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                 @Override
//                                                                 public void onComplete(@NonNull Task<Void> task) {
//                                                                     if (task.isSuccessful()) {
//                                                                         Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
//                                                                         DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodList");
//                                                                         Data data = new Data(arrayList.get(position).getName(), arrayList.get(position).getUrl(), arrayList.get(position).getPrice(), arrayList.get(position).getDespcription(), String.valueOf(Integer.valueOf(arrayList.get(position).getInStock()) - number));
//                                                                         databaseReference.child(arrayList.get(position).getName()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                             @Override
//                                                                             public void onComplete(@NonNull Task<Void> task) {
//                                                                                 if (task.isSuccessful()) {
//                                                                                     Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//                                                                                 }
//                                                                             }
//                                                                         });
//                                                                     } else {
//                                                                         Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//                                                                     }
//                                                                 }
//                                                             });
                                                         }
                                                             else {
                                                                 Toast.makeText(context, "Sorry, Your order is exceeded the limit.", Toast.LENGTH_SHORT).show();
                                                             }
                                                     }
                                                     });
                                                     builder.setView(view1).show();
                                                 }
                                             }
                                         }
        );
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
