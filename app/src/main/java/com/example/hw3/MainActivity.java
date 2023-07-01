package com.example.hw3;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //hide title bar
        getSupportActionBar().hide();                   //
        setContentView(R.layout.activity_main);

        ImageView mk_watermark = (ImageView) findViewById(R.id.mk_watermark);                                           //
        ObjectAnimator animation = ObjectAnimator.ofFloat(mk_watermark, "rotationY", 0.0f, 360f);    //
        animation.setDuration(3600);                                                                                    // spinning watermark code
        animation.setRepeatCount(ObjectAnimator.INFINITE);                                                              //
        animation.setInterpolator(new LinearInterpolator());                                                            //
        animation.start();                                                                                              //

    }//onCreate

    public void button_GotoMaps (View view){
        Intent intent = new Intent(this, MapsActivity.class);   //go to maps activity
        startActivity(intent);                                              //
    }//button_GotoMaps

    public void button_Share (View view){
        //TODO: Make this functional
    }//button_Share

    public void button_GotoMenu (View view){
        Intent intent = new Intent(this, MenuActivity.class);   //go to menu activity
        startActivity(intent);                                              //
    }//button_GotoMenu
}