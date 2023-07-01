package com.example.hw3;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;

    ItemListAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }//ItemListAdapter

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.itemrecycler_row, parent, false);
        return new ViewHolder(view);
    }//onCreateViewHolder

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = mData.get(position);
        holder.myTextView.setText(item);
        holder.myTextView.setTextColor(Color.parseColor("#450A0A"));
    }//onBindViewHolder

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }//getItemCount


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView myTextView2;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.itemName);
            myTextView2 = itemView.findViewById(R.id.itemPrice);
            itemView.setOnClickListener(this);
        }//ViewHolder

        @Override
        public void onClick(View view) {
                                                                      //
        }//onClick
    }//ViewHolder

    // convenience method for getting data at click position
    //String getItem(int id) {
    //    return mData.get(id);
    //}//getItem
    
}//ItemListAdapter
