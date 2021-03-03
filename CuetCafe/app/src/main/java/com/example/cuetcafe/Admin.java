package com.example.cuetcafe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

    }

    public void Add(View view) {
       dotask();
    }

    private void dotask() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view1=getLayoutInflater().inflate(R.layout.addproduct,null);
        EditText p,u,d,a;

        Spinner n;
        Button add;
        n=view1.findViewById(R.id.productname);
//        ArrayList arrayList=new ArrayList<>();
//        arrayList.add("Birani");
//        arrayList.add("Pizza");
//        arrayList.add("Singra");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Admin.this,
//                android.R.layout.simple_dropdown_item_1line, arrayList);
//        n.setAdapter(adapter);
        d=view1.findViewById(R.id.productdescription);
        a=view1.findViewById(R.id.productamount);
        p=view1.findViewById(R.id.productprice);
        u=view1.findViewById(R.id.producturi);
        add=view1.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name,price,uri,description,amount;
                amount=a.getText().toString();
                name=n.getSelectedItem().toString();
                uri=u.getText().toString();
                description=d.getText().toString();
                amount=a.getText().toString();
                price=p.getText().toString();
                Data data=new Data(name,uri,price,description,amount);
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("FoodList");
                databaseReference.child(name.trim()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Admin.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        builder.setView(view1).show();
    }

}