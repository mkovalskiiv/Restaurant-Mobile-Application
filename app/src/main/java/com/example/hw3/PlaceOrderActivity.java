package com.example.hw3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceOrderActivity extends AppCompatActivity {

    private OrderViewModel mOrderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
    }//onCreate

    public void button_SubmitOrder (View view){

        TextView mOrderName = (TextView) findViewById(R.id.edittxtName);
        TextView mOrderEmail = (TextView) findViewById(R.id.edittxtEmail);
        TextView mOrderPhone = (TextView) findViewById(R.id.edittxtPhone);
        TextView mOrderDate = (TextView) findViewById(R.id.edittxtDate);
        Double mOrderAmount = CartActivity.total;

        // Insert order info into database
        Order order = new Order();
        //Order order = new Order(999, "", "", "", "", "");
        order.setOrderName(mOrderName.getText().toString());
        order.setOrderEmail(mOrderEmail.getText().toString());
        order.setOrderPhone(mOrderPhone.getText().toString());
        order.setOrderDate(mOrderDate.getText().toString());
        order.setOrderAmount(String.valueOf(mOrderAmount));
        mOrderViewModel.insert(order);

        Toast toasty = Toast.makeText(this, "Thank you! Your order has been placed.", Toast.LENGTH_SHORT);    //initialize toast message
        toasty.show();      //display toast message

        Intent intent = new Intent(this, MainActivity.class);  //go to main activity
        startActivity(intent);                                              //
    }//button_SubmitOrder

    public void button_Cancel (View view){
        Intent intent = new Intent(this, MenuActivity.class);  //go to menu activity
        startActivity(intent);                                              //
    }//button_SubmitOrder

}//PlaceOrderActivity