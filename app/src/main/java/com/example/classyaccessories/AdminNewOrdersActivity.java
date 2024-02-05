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
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classyaccessories.Model.AdminOrders;
import com.example.classyaccessories.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.android.material.transition.MaterialSharedAxis.X;

public class AdminNewOrdersActivity extends AppCompatActivity {
    private RecyclerView orderList;
    private DatabaseReference orderRef;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        orderList = findViewById(R.id.orders_list);
        orderList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        orderList.setLayoutManager(layoutManager);




        orderList.setLayoutManager(new LinearLayoutManager(this));
        //orderRef= FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin view").child(Prevalent.currentOnlineUser.getPhone()).child("Products");
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
    }

    protected void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<AdminOrders>options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(orderRef,AdminOrders.class)
                .build();
        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>adapter=
                new FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int positive, @NonNull AdminOrders model) {
                        holder.userName.setText("Name: " + model.getName());
                        holder.userPhoneNumber.setText("Phone: " + model.getPhone());
                        holder.userTotalPrice.setText("Total Price: " + model.getTotalAmount());
                        holder.userDateTime.setText("Order At: " + model.getDate() + " "+model.getTime());
                        holder.userShippingAddress.setText("Shipping Address: " +model.getAddress() + "    city: "+model.getCity());




                       /* holder.showordersbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(positive).getKey();
                                Intent intent= new Intent(AdminNewOrdersActivity.this, adminUserProductsCartActivity.class);
                                intent.putExtra("uid", model.getPhone());
                                startActivity(intent);
                            }
                        });*/






                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                                builder.setTitle("Are you want to cancel this order?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which == 1)
                                        {
                                            String uID=getRef(positive).getKey();
                                            RemoverOrder(uID);

                                        }
                                        else {

                                            finish();
                                        }

                                    }
                                });

                                /*builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin view")
                                                .child(Prevalent.currentOnlineUser.getPhone())
                                                .child("Products")
                                                .child(model.getPid())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                        {
                                                            Toast.makeText(AdminNewOrdersActivity.this, "Items removed"+Prevalent.currentOnlineUser.getPhone(),
                                                                    Toast.LENGTH_SHORT).show();
                                                            Intent intent =new Intent(AdminNewOrdersActivity.this,
                                                                    AdminCategoryActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            finish();
                                                        }else{
                                                            Toast.makeText(AdminNewOrdersActivity.this, "Failed to remove" , Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });



                                    }
                                });*/
                                builder.show();

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return  new AdminOrdersViewHolder(view);
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();

    }



    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{
        private TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
       /* private Button showordersbtn, rejectbtn,acceptbtn;*/

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName =itemView.findViewById(R.id.order_user_name);
            userPhoneNumber =itemView.findViewById(R.id.order_phone_number);
            userTotalPrice =itemView.findViewById(R.id.order_total_price);
            userDateTime =itemView.findViewById(R.id.order_date_time);
            userShippingAddress =itemView.findViewById(R.id.order_address_city);
            /*showordersbtn = itemView.findViewById(R.id.show_all_products);
            rejectbtn = itemView.findViewById(R.id.cancel);
            acceptbtn = itemView.findViewById(R.id.accept);*/



        }
    }

    private void RemoverOrder(String uID) {
        orderRef.child(uID).removeValue();
    }
}