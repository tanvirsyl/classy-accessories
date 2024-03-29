package com.example.classyaccessories;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classyaccessories.Model.Cart;
import com.example.classyaccessories.Prevalent.Prevalent;
import com.example.classyaccessories.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cart_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount, txtMag1;
    private int overTotalPrice =0;
    private DatabaseReference cartListRef;
    private String productID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount =(TextView) findViewById(R.id.total_priceb);
        txtMag1 =(TextView) findViewById(R.id.msg1);


        cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("User view").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products");


        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtTotalAmount.setText("Total Price =  ৳" + String.valueOf(overTotalPrice));
                Intent intent =new Intent(Cart_Activity.this, ComfirmFinalOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });
                ///
            }
    @Override
    protected void onStart() {
        super.onStart();
        //CheckOrderState();


        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef, Cart.class)
                                .build();


        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter   = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options){

            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                holder.txtProductName.setText(model.getPname());
                holder.getTxtProductPrice.setText("Price "+model.getPrice()+"৳");
                holder.getTxtProductQuantity.setText("Quantity"+model.getQuantity());



                //int oneTypeProductTPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                /*String dPrice = model.getPrice();
                String dPriceb = "0";
                int len =   dPrice.length();
                for(int i = 0; i<len; i++){
                    if(dPrice.charAt(i) != ','){
                        char ch = dPrice.charAt(i);
                        dPriceb += dPriceb+ch;
                    }
                }*/
                //Toast.makeText(Cart_Activity.this, "Price = " +dPriceb, Toast.LENGTH_SHORT).show();
                overTotalPrice = overTotalPrice + (Integer.parseInt(model.getPrice()) * Integer.parseInt(model.getQuantity()));
                txtTotalAmount.setText("Total Price =  ৳" + overTotalPrice);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Cart_Activity.this, "TEST" , Toast.LENGTH_SHORT).show();

                        CharSequence options[] = new CharSequence[]
                                {

                                        "Remove"
                                };


                        AlertDialog.Builder builder = new AlertDialog.Builder(Cart_Activity.this);
                        builder.setTitle("Cart options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                   /* FirebaseDatabase.getInstance().getReference().child("User view")
                                            .child(Prevalent.currentOnlineUser.getPhone())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()*/
                                    FirebaseDatabase.getInstance().getReference().child("Cart List").child("User view")
                                            .child(Prevalent.currentOnlineUser.getPhone())
                                        .child("Products")
                                        .child(model.getPid())
                                        .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(Cart_Activity.this, "Items removed"+Prevalent.currentOnlineUser.getPhone(),
                                                                Toast.LENGTH_SHORT).show();
                                                        Intent intent =new Intent(Cart_Activity.this,
                                                                HomeActivity.class);
                                                        startActivity(intent);
                                                    }else{
                                                        Toast.makeText(Cart_Activity.this, "Failed to remove" , Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });



                            }
                        });
                        builder.show();
                    }
                });

            }
            //;

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String shippingState = snapshot.child("state").getValue().toString();
                    String userName = snapshot.child("state").getValue().toString();

                    if (shippingState.equals("shipped"))
                    {
                        txtTotalAmount.setText("Dear" + userName + "\n order is shipped successfully.");
                        recyclerView.setVisibility(View.GONE);

                        txtMag1.setVisibility(View.VISIBLE);
                        NextProcessBtn.setVisibility(View.GONE);

                        Toast.makeText(Cart_Activity.this, "you can purchase more products, " +
                                "once you received your first final order.", Toast.LENGTH_SHORT).show();

                    }
                    else if (shippingState.equals("not shipped"))
                    {
                        txtTotalAmount.setText("Shipping state = Not shipped");
                        recyclerView.setVisibility(View.GONE);

                        txtMag1.setVisibility(View.VISIBLE);
                        txtMag1.setText("Congratulation, your final order has been shipped successfully. " +
                                "Soon you will received your order at your door step");
                        NextProcessBtn.setVisibility(View.GONE);

                        Toast.makeText(Cart_Activity.this, "you can purchase more products, " +
                                "once you received your first final order.", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}