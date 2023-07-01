package com.example.hw3;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    public static Integer numBurgers = 0;
    public static Integer numHotDogs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //hide title bar
        getSupportActionBar().hide();                   //
        setContentView(R.layout.activity_menu);

        ImageView mk_watermark = (ImageView) findViewById(R.id.mk_watermark2);                                          //
        ObjectAnimator animation = ObjectAnimator.ofFloat(mk_watermark, "rotationY", 0.0f, 360f);    //
        animation.setDuration(3600);                                                                                    // spinning watermark code
        animation.setRepeatCount(ObjectAnimator.INFINITE);                                                              //
        animation.setInterpolator(new LinearInterpolator());                                                            //
        animation.start();                                                                                              //

        TextView numBurgersInCart = (TextView) findViewById(R.id.txtNumBurger); //
        TextView numHotDogsInCart = (TextView) findViewById(R.id.txtNumHotDog); // initialize displayed number of items
        numBurgersInCart.setText(String.valueOf(numBurgers));                   // to default value (zero)
        numHotDogsInCart.setText(String.valueOf(numHotDogs));                   //

        CartActivity.ItemList.removeAll(CartActivity.ItemList);     //delete all elements in ItemList
        CartActivity.PriceList.removeAll(CartActivity.PriceList);     //delete all elements in ItemList
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

    public void button_BurgerAdd (View view){
        numBurgers++; // increment numBurgers
        updateBurger(); // update value displayed
    }//button_BurgerAdd

    public void button_BurgerRemove (View view){
        numBurgers--; // decrement numBurgers
        if (numBurgers < 0) numBurgers = 0; // prevent negative values
        updateBurger(); // update value displayed
    }//button_BurgerRemove

    public void button_HotDogAdd (View view){
        numHotDogs++; // increment numHotDogs
        updateHotDog(); // update value displayed
    }//button_HotDogAdd

    public void button_HotDogRemove (View view){
        numHotDogs--;   // decrement numHotDogs
        if (numHotDogs < 0) numHotDogs = 0; // prevent negative values
        updateHotDog(); // update value displayed
    }//button_HotDogRemove

    public void updateHotDog(){
        TextView numHotDogsInCart = (TextView) findViewById(R.id.txtNumHotDog); //
        numHotDogsInCart.setText(String.valueOf(numHotDogs));                   // update value displayed
    }//updateHotDog

    public void updateBurger(){
        TextView numBurgersInCart = (TextView) findViewById(R.id.txtNumBurger); //
        numBurgersInCart.setText(String.valueOf(numBurgers));                   // update value displayed
    }//updateBurger


}//MenuActivity