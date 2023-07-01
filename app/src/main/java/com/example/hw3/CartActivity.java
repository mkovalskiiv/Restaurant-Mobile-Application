package com.example.hw3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.DecimalFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CartActivity extends AppCompatActivity {

    public static Double priceBurgers = 0.00;       // initialize priceBurgers
    public static Double priceHotDogs = 0.00;       // initialize priceHotDogs
    public static Double subtotal = 0.00;
    public static Double total = 0.00;
    public static ArrayList<String> ItemList = new ArrayList<String>();     // initialize ItemList ArrayList
    public static ArrayList<String> PriceList = new ArrayList<String>();     // initialize PriceList ArrayList
    public static ArrayList<String> RecyclerList = new ArrayList<String>(); // initialize RecyclerList ArrayList
    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");  // used to show decimals in $#.## format

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //hide title bar
        getSupportActionBar().hide();                   //
        setContentView(R.layout.activity_cart);

        ImageView mk_watermark = (ImageView) findViewById(R.id.mk_watermark3);                                          //
        ObjectAnimator animation = ObjectAnimator.ofFloat(mk_watermark, "rotationY", 0.0f, 360f);    //
        animation.setDuration(3600);                                                                                    // spinning watermark code
        animation.setRepeatCount(ObjectAnimator.INFINITE);                                                              //
        animation.setInterpolator(new LinearInterpolator());                                                            //
        animation.start();                                                                                              //

        priceBurgers = MenuActivity.numBurgers * 6.00;  // Calculate totals for each item
        priceHotDogs = MenuActivity.numHotDogs * 3.00;  // Calculate totals for each item

        if (MenuActivity.numBurgers > 0.00) {
            ItemList.add("Cheeseburger (" + String.valueOf(MenuActivity.numBurgers) + ")");
            PriceList.add("$" + String.valueOf(decimalFormatter.format(priceBurgers)));
        }//if

        if (MenuActivity.numHotDogs > 0.00) {
            ItemList.add("Hot Dog (" + String.valueOf(MenuActivity.numHotDogs) + ")");
            PriceList.add("$" + String.valueOf(decimalFormatter.format(priceHotDogs)));
        }//if

        ListView itemList = (ListView) findViewById(R.id.lstItemList);                                                                        //
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CartActivity.ItemList );    // update item listview
        itemList.setAdapter(arrayAdapter);                                                                                                    //
        arrayAdapter.notifyDataSetChanged();

        ListView priceList = (ListView) findViewById(R.id.lstPriceList);                                                                        //
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CartActivity.PriceList );    // update price listview
        priceList.setAdapter(arrayAdapter2);                                                                                                    //
        arrayAdapter2.notifyDataSetChanged();



        // RECYCLER VIEW STUFF STARTS

        itemList.setAlpha(0);       // make both of the old
        priceList.setAlpha(0);      // lists invisible, so I can keep the code
                                    // for totals calculation

        RecyclerList.clear();   //clear out old values of recyclerlist arraylist to prevent duplicates

        for (int i = 0; i < ItemList.size(); i++) {
            RecyclerList.add(ItemList.get(i) + ",   " + PriceList.get(i));
        }//for

        RecyclerView itemListRecycler = (RecyclerView) findViewById(R.id.recyclerItemList);
        itemListRecycler.setLayoutManager(new LinearLayoutManager(this));
        ItemListAdapter itemAdapter = new ItemListAdapter(this, RecyclerList);
        itemListRecycler.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();


        // RECYCLER VIEW STUFF ENDS


        TextView subtotalView = (TextView) findViewById(R.id.txtSubtotal);
        subtotal = priceBurgers + priceHotDogs;
        subtotalView.setText("Subtotal: $" + String.valueOf(decimalFormatter.format(subtotal)));

        Spinner spinner = (Spinner) findViewById(R.id.spnTips);
        ArrayAdapter<CharSequence> adapterTips = ArrayAdapter.createFromResource(this, R.array.tips_array, android.R.layout.simple_spinner_item);
        adapterTips.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterTips);


        TextView totalView = (TextView) findViewById(R.id.txtTotal);            //
        TextView customTip = (TextView) findViewById(R.id.edittxtCustomTip);    // initialize tip views
        Spinner spinnerTips = (Spinner) findViewById(R.id.spnTips);             //

        spinnerTips.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                calculateTotal();   // calculate totals
            }//onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // do nothing
            }//onNothingSelected
        });//setOnItemSelectedListener


        customTip.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }//afterTextChanged

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }//beforeTextChanged

            @Override
            public void onTextChanged(CharSequence stringy, int start, int before, int count) {
                if(stringy.length() == 0)             //
                    customTip.setText("1.0");   // catch empty tip string
                calculateTotal();   // calculate totals
            }//onTextChanged
        });//addTextChangedListener

        //
        // DISPLAY NOTIFICATION IF THE RESTAURANT IS CLOSED
        //
        // HOUR_OF_DAY is the current hour in 24-hour time. Since the store opens/closes on
        // the hour, minutes are of no concern here. The comparison checks if the current
        // hour is either before the restaurant opens (11AM, 11 in 24-hour time) or after
        // the restaurant closes (9PM, or 21 in 24-hour time). If either case is true, the
        // message will display, and the "PLACE ORDER" button will be deactivated.
        //

        Toast toasty = Toast.makeText(this, "Our operational hours are 11AM to 9PM.\n" +
                                                        "Please wait until the restaurant is open before placing an order.", Toast.LENGTH_LONG);    // initialize toast
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Boston"));    // initialize calendar for time retrieval
        Button button_PlaceOrder = (Button) findViewById(R.id.btnSubmitOrder);

        if(now.get(Calendar.HOUR_OF_DAY) < 11 || now.get(Calendar.HOUR_OF_DAY) > 21) {  // if current time is before 11AM or after 9PM
            toasty.show();      //display toast
            button_PlaceOrder.setEnabled(false);            //disable button
            button_PlaceOrder.getBackground().setAlpha(65); //set button to low opacity
            button_PlaceOrder.setTextColor(Color.parseColor("#66FF0000"));  //set button text color to lower value
        } else {
            button_PlaceOrder.setEnabled(true);     //enable button
            button_PlaceOrder.getBackground().setAlpha(255);        //set button to normal opacity
            button_PlaceOrder.setTextColor(Color.parseColor("#450A0A"));  //set button text color to original value
        }//if-else

    }//onCreate

    public void button_SubmitOrder (View view){
        Intent intent = new Intent(this, PlaceOrderActivity.class);  //go to place order activity
        startActivity(intent);                                              //
    }//button_SubmitOrder

    public void button_GotoMenu (View view){
        Intent intent = new Intent(this, MenuActivity.class);  //go to menu activity
        startActivity(intent);                                              //
    }//button_GotoMenu

    public void button_GotoCart (View view){
        Intent intent = new Intent(this, CartActivity.class);  //go to cart activity
        startActivity(intent);                                              //
    }//button_GotoCart

    public void button_GotoOrders (View view){
        Intent intent = new Intent(this, OrdersActivity.class); //go to orders activity
        startActivity(intent);                                              //
    }//button_GotoOrders

    public void button_GotoMain (View view){
        Intent intent = new Intent(this, MainActivity.class);  //go to main activity
        startActivity(intent);                                              //
    }//button_GotoMain

    public void calculateTotal() {
        TextView totalView = (TextView) findViewById(R.id.txtTotal);            //
        TextView customTip = (TextView) findViewById(R.id.edittxtCustomTip);    // initialize tip views
        Spinner spinnerTips = (Spinner) findViewById(R.id.spnTips);             //

        Double customTipAmoount = Double.parseDouble(customTip.getText().toString());
        if (spinnerTips.getSelectedItem().equals("15% Tip")) {
            total = subtotal * 1.15;
            totalView.setText("Total: $" + String.valueOf(decimalFormatter.format(subtotal * 1.15)));
        }
        if (spinnerTips.getSelectedItem().equals("18% Tip")) {
            total = subtotal * 1.18;
            totalView.setText("Total: $" + String.valueOf(decimalFormatter.format(subtotal * 1.18)));
        }
        if (spinnerTips.getSelectedItem().equals("20% Tip")) {
            total = subtotal * 1.20;
            totalView.setText("Total: $" + String.valueOf(decimalFormatter.format(subtotal * 1.20)));
        }
        if (spinnerTips.getSelectedItem().equals("Custom Tip")) {
            total = subtotal + (subtotal * (customTipAmoount * 0.01));
            totalView.setText("Total: $" + String.valueOf(decimalFormatter.format(subtotal + (subtotal * (customTipAmoount * 0.01)))));
        }

    }//calculateTotal

}//CartActivity