package com.example.hw3;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="order_info")
public class Order {
    @PrimaryKey(autoGenerate=true)
    @NonNull
    private int orderid;

    @ColumnInfo(name = "order_name")
    private String orderName;

    @ColumnInfo(name = "order_email")
    private String orderEmail;

    @ColumnInfo(name = "order_phone")
    private String orderPhone;

    @ColumnInfo(name = "order_date")
    private String orderDate;

    @ColumnInfo(name = "order_amount")
    private String orderAmount;

    public int getOrderid() {
        return orderid;
    }
    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getOrderName() { return orderName; }
    public void setOrderName(String orderName) { this.orderName = orderName; }

    public String getOrderEmail() { return orderEmail; }
    public void setOrderEmail(String orderEmail) { this.orderEmail = orderEmail; }

    public String getOrderPhone() { return orderPhone; }
    public void setOrderPhone(String orderPhone) { this.orderPhone = orderPhone; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public String getOrderAmount() { return orderAmount; }
    public void setOrderAmount(String orderAmount) { this.orderAmount = orderAmount; }

    /*public Order(Integer orderid, String orderName, String orderEmail, String orderPhone, String orderDate, String orderAmount) {
        setOrderid(orderid);
        setOrderName(orderName);
        setOrderEmail(orderEmail);
        setOrderPhone(orderPhone);
        setOrderDate(orderDate);
        setOrderAmount(orderAmount);
    }//Order*/
}//Order

