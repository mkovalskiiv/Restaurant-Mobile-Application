package com.example.hw3;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrderRepository mRepository;

    private LiveData<List<Order>> mAllOrders;

    public OrderViewModel (Application application) {
        super(application);
        mRepository = new OrderRepository(application);
        mAllOrders = mRepository.getAllOrders();
    }

    LiveData<List<Order>> getAllOrders() { return mAllOrders; }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void deleteSingleOrder(Order order){
        mRepository.deleteSingleOrder(order);
    }

    public void insert(Order order) { mRepository.insert(order); }

}
