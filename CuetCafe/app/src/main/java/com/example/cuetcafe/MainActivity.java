package com.example.cuetcafe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ArrayList<Data> arrayList;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    CartAdapter cartAdapter;
    int total_Amount=0;
    ArrayList<Cart> arrayList1;
    int value=2;
    FirebaseAuth firebaseAuth;
    String orderstatus="You didn't make any order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Getting Server Data");
        progressDialog.show();
        progressDialog.setCancelable(false);
        recyclerView=findViewById(R.id.recyclerview);
        arrayList=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
//        Data data4=new Data("Shahin Bashar","https://i.imgur.com/jDr4GqN.jpg","100","It's very delicious","50");
//        Data data3=new Data("Shahin Bashar","https://i.imgur.com/jDr4GqN.jpg","100","It's very delicious","50");
//        arrayList.add(data4);
//        arrayList.add(data3);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("FoodList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                if (snapshot.exists()) {
                    progressDialog.hide();
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        Data data= dataSnapshot1.getValue(Data.class);
                        arrayList.add(data);
                    }
                    Collections.reverse(arrayList);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this);
                    linearLayoutManager.setSmoothScrollbarEnabled(true);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    myAdapter=new MyAdapter(MainActivity.this,arrayList);
                    recyclerView.setAdapter(myAdapter);

                } else {
                    Toast.makeText(MainActivity.this, "There are no message", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.admin:
                admin();
                return true;
            case R.id.signout:
                signout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signout() {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }

    private void admin() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.check,null);
        EditText editText;
        Button button;
        editText=view.findViewById(R.id.pass);
        button=view.findViewById(R.id.adminbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=editText.getText().toString();
                if(p.equals("2222")){
                    startActivity(new Intent(MainActivity.this,Admin.class));
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this, "False", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(view).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CheckOut(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view1=getLayoutInflater().inflate(R.layout.cartview,null);
        RecyclerView recyclerView1=view1.findViewById(R.id.cartrecaycle);
        TextView total=view1.findViewById(R.id.totalamount);
        arrayList1=new ArrayList();
        Button checkout=view1.findViewById(R.id.checkout);
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
//        LocalDateTime now = LocalDateTime.now();
//        String s=dtf.format(now);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    total_Amount=0;
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        Cart data= dataSnapshot1.getValue(Cart.class);
                        total_Amount=Integer.valueOf(data.getTaka())+total_Amount;
                        arrayList1.add(data);
                    }
                    total.setText("Total Amount: "+total_Amount+" TK");
                    Collections.reverse(arrayList1);
                } else {
                    Toast.makeText(MainActivity.this, "There are no message", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(linearLayoutManager);
        cartAdapter=new CartAdapter(this,arrayList1);
        recyclerView1.setAdapter(cartAdapter);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
                View view2=getLayoutInflater().inflate(R.layout.checkout,null);
                EditText address,number,name;
                Spinner paymenttype;
                FloatingActionButton done;
                name=view2.findViewById(R.id.name);
                address=view2.findViewById(R.id.tableno);
                number=view2.findViewById(R.id.number);
                paymenttype=view2.findViewById(R.id.paymenttype);
                done=view2.findViewById(R.id.confirm);
                done.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        String na,a,n,p;
                        na=name.getText().toString();
                        a=address.getText().toString();
                        n=number.getText().toString();
                        p=paymenttype.getSelectedItem().toString();
                        if(p.equals("Bkash")){
                            Toast.makeText(MainActivity.this, "It's under processing.", Toast.LENGTH_SHORT).show();
                        }else {
                            ProgressDialog progressDialog= new ProgressDialog(MainActivity.this);
                            progressDialog.show();
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Order");
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
                            LocalDateTime now = LocalDateTime.now();
                            String s=dtf.format(now);
                            String id=firebaseAuth.getUid();
                            Order order=new Order(arrayList1,na,n,a,"30");
                            databaseReference.child(s).child(firebaseAuth.getUid()).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                   DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
                                   databaseReference2.removeValue();
                                   CountDownTimer countDownTimer=new CountDownTimer(5000,1000) {
                                       @Override
                                       public void onTick(long millisUntilFinished) {
                                           String v=firebaseAuth.getUid();
                builder.setMessage("Your Order is Submitted and It'll will be completed around "+orderstatus+" Minutes after submission\nThank You.").show();
                                           progressDialog.setMessage("Your Order is Successfully submitted.\nYour Token Number is "+v.substring(v.length()-4,v.length())+"\nYour Delivary will be completed within "+value+" minutes from your order time.\nThank you");
                                           if(millisUntilFinished/1000==0){
                                           startActivity(new Intent(MainActivity.this,MainActivity.class));
                                               progressDialog.hide();
                                           }
                                       }

                                       @Override
                                       public void onFinish() {

                                       }
                                   }.start();
                               }else {
                                   Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                               }
                                }
                            });
                        }
                    }
                });

                builder1.setView(view2).show();
            }
        });
        builder.setView(view1).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Search(View view) {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        String s=dtf.format(now);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Order");
        databaseReference.child(s).child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           if(snapshot.exists()){
               Order order=snapshot.getValue(Order.class);
               value=Integer.valueOf(order.getEstimatetime());
               CountDownTimer countDownTimer=new CountDownTimer(5000,1000) {
                   @Override
                   public void onTick(long millisUntilFinished) {
                       String v=firebaseAuth.getUid();
//                builder.setMessage("Your Order is Submitted and It'll will be completed around "+orderstatus+" Minutes after submission\nThank You.").show();
                       progressDialog.setMessage("Your Token Number is "+v.substring(v.length()-4,v.length())+"\nYour order will be delivered around "+value+" Minutes after submission.\nThank you");
                       if(millisUntilFinished/1000==0){
                           progressDialog.hide();
                       }
                   }

                   @Override
                   public void onFinish() {

                   }
               }.start();
           }else {
               progressDialog.hide();
               Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        }
}
