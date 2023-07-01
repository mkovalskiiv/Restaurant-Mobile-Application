package com.example.hw3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * from order_info ORDER BY orderid ASC")
    LiveData<List<Order>> getAllOrders();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Order order);

    @Query("DELETE FROM order_info")
    void deleteAll();

    @Query("DELETE FROM order_info WHERE orderid = :orderid")
    abstract void deleteOrder2(Integer orderid);

    @Delete
    void deleteOrder(Order order);
}
