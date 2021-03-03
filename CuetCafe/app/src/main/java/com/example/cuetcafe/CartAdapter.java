package com.example.cuetcafe;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    Context context;
    ArrayList<Cart> arrayList;
    Data data1;
    public CartAdapter(Context context,ArrayList<Cart> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.cart,parent,false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.name.setText("Food Name: "+arrayList.get(position).getName());
        holder.price.setText("Total Price: "+arrayList.get(position).getTaka()+" Tk");
        holder.amount.setText("Total Amount: "+arrayList.get(position).getAmount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Check", Toast.LENGTH_SHORT).show();
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
                databaseReference.child(arrayList.get(position).getName()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){
                       ProgressDialog progressDialog=new ProgressDialog(view.getContext());
                       progressDialog.show();
                       progressDialog.setCancelable(false);
                       CountDownTimer countDownTimer=new CountDownTimer(3000,1000) {

                           @Override
                           public void onTick(long millisUntilFinished) {
                               progressDialog.setMessage(String.valueOf(millisUntilFinished/1000));
                               if(millisUntilFinished/1000==0){
                                   progressDialog.hide();
                                   context.startActivity(new Intent(context,MainActivity.class));
                               }
                           }

                           @Override
                           public void onFinish() {

                           }
                       }.start();

                       Toast.makeText(view.getContext(), "SUccess", Toast.LENGTH_SHORT).show();
//                       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodList");
//                       DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("FoodList");
//                       databaseReference1.child(arrayList.get(position).getName()).addValueEventListener(new ValueEventListener() {
//                           @Override
//                           public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                data1=snapshot.getValue(Data.class);
//                           }
//
//                           @Override
//                           public void onCancelled(@NonNull DatabaseError error) {
//
//                           }
//                       });
//                       databaseReference.child(arrayList.get(position).getName()).child("inStock").setValue(Integer.valueOf(data1.getInStock())+arrayList.get(position).getAmount()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                           @Override
//                           public void onComplete(@NonNull Task<Void> task) {
//                               if (task.isSuccessful()) {
//                                   Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//                               }
//                           }
//                       });
//                       Toast.makeText(context, "Your Cart is updated.", Toast.LENGTH_SHORT).show();
                   }else {
                       Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                   }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
