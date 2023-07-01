package com.example.hw3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private OrderViewModel mOrderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //hide title bar
        getSupportActionBar().hide();                   //
        setContentView(R.layout.activity_orders);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerOrdersList);

        OrderListAdapter mAdapter = new OrderListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);


        mOrderViewModel.getAllOrders().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(@Nullable final List<Order> orders) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setOrders(orders);

            }
        });

        mAdapter.notifyDataSetChanged();
    }//onCreate

    public void button_GotoMain (View view){
        Intent intent = new Intent(this, MainActivity.class);  //go to main activity
        startActivity(intent);                                              //
    }//button_GotoMain

    public void button_GotoCart (View view){
        Intent intent = new Intent(this, CartActivity.class);  //go to cart activity
        startActivity(intent);                                              //
    }//button_GotoCart

    public void button_GotoOrders (View view){
        Intent intent = new Intent(this, OrdersActivity.class); //go to orders activity
        startActivity(intent);                                              //
    }//button_GotoOrders

    public void button_GotoMenu (View view){
        Intent intent = new Intent(this, MenuActivity.class);  //go to menu activity
        startActivity(intent);                                              //
    }//button_GotoMenu

    public void button_deleteAllOrders (View view){
        mOrderViewModel.deleteAll();
        Intent intent = new Intent(this, MenuActivity.class);  //go to menu activity
        startActivity(intent);                                              //
    }//button_deleteAllOrders

    public void button_deleteSingleOrder (View view){
        RecyclerView mRecyclerView = findViewById(R.id.recyclerOrdersList);
        EditText mOrderEditText = findViewById(R.id.edittxtOrderToDelete);

        Order order = new Order();
        order.setOrderid(Integer.parseInt(mOrderEditText.getText().toString()));
        order.setOrderName("dumb");
        order.setOrderEmail("dumb");
        order.setOrderPhone("dumb");
        order.setOrderDate("dumb");
        order.setOrderAmount("dumb");

        mOrderViewModel.deleteSingleOrder(order);
        Intent intent = new Intent(this, MenuActivity.class);  //go to menu activity
        startActivity(intent);                                              //
    }//button_deleteAllOrders
}