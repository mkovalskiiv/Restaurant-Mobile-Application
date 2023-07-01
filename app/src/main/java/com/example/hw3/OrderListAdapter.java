package com.example.hw3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>{

    private List<Order> mOrderList;
    private final LayoutInflater mInflater;
    private OrderViewModel mOrderViewModel;



    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView orderIdTextView;
        public final TextView orderNameTextView;
        public final TextView orderEmailTextView;
        public final TextView orderPhoneTextView;
        public final TextView orderDateTextView;
        public final TextView orderAmountTextView;
        public final RecyclerView orderListRecyclerView2;

        final OrderListAdapter mAdapter;


        public OrderViewHolder(View itemView, OrderListAdapter adapter) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderID);
            orderNameTextView = itemView.findViewById(R.id.orderName);
            orderEmailTextView = itemView.findViewById(R.id.orderEmail);
            orderPhoneTextView = itemView.findViewById(R.id.orderPhone);
            orderDateTextView = itemView.findViewById(R.id.orderDate);
            orderAmountTextView = itemView.findViewById(R.id.orderAmount);
            orderListRecyclerView2 = itemView.findViewById(R.id.recyclerOrdersList);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            // Use that to access the affected item in mWordList.
            Order element = mOrderList.get(mPosition);
            // Change the word in the mWordList.


        }

    }

    public void setOrders(List<Order> orderList){
        mOrderList = orderList;
        notifyDataSetChanged();
    }

    public OrderListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public OrderListAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.ordersrecycler_row, parent, false);
        return new OrderViewHolder(mItemView, this);
    }


    @Override
    public void onBindViewHolder(OrderListAdapter.OrderViewHolder holder,
                                 int position) {
        // Retrieve the data for that position.
        Order mCurrent = mOrderList.get(position);
        // Add the data to the view holder.
        holder.orderIdTextView.setText(mCurrent.getOrderid()+"");
        holder.orderNameTextView.setText(mCurrent.getOrderName());
        holder.orderEmailTextView.setText(mCurrent.getOrderEmail());
        holder.orderPhoneTextView.setText(mCurrent.getOrderPhone());
        holder.orderDateTextView.setText(mCurrent.getOrderDate());
        holder.orderAmountTextView.setText(mCurrent.getOrderAmount());

    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(mOrderList ==null){
            return 0;
        }
        return mOrderList.size();
    }

    public void deleteOrder(String orderid, String ordername, String orderemail, String orderphone, String orderdate, String orderamount) {
        //TODO: Make the button in the view list delete the entry

        final String tag = "OrderListAdapter";
        final String message1 = "\n\n\nDeleting order with values of " + orderid + ", " + ordername + ", " + orderemail + ", " + orderphone + ", " + orderdate + ", " + orderamount;
        Log.d(tag, message1);

        //Order order = new Order(999, "", "", "", "", "");
        Order order = new Order();
        order.setOrderid(Integer.parseInt(orderid));
        order.setOrderName(ordername);
        order.setOrderEmail(orderemail);
        order.setOrderPhone(orderphone);
        order.setOrderDate(orderdate);
        order.setOrderAmount(orderamount);

        final String message2 = "\n\n\nChecking order values, it is: "
                                + order.getOrderid()
                                + ", "
                                + order.getOrderName()
                                + ", "
                                + order.getOrderEmail()
                                + ", "
                                + order.getOrderPhone()
                                + ", "
                                + order.getOrderDate()
                                + ", "
                                + order.getOrderAmount();
        Log.d(tag, message2);

        mOrderViewModel.deleteSingleOrder(order);
        //mOrderViewModel.deleteAll();
    }//deleteOrder
}
