package com.example.hw3;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class OrderRepository {
    private OrderDao mOrderDao;
    private LiveData<List<Order>> mAllOrders;

    OrderRepository(Application application) {
        OrderRoomDatabase db = OrderRoomDatabase.getDatabase(application);
        mOrderDao = db.orderDao();
        mAllOrders = mOrderDao.getAllOrders();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Order>> getAllOrders() {
        return mAllOrders;
    }

    public void deleteAll(){
        new deleteAsyncTask(mOrderDao).execute();
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Order order) {
        new insertAsyncTask(mOrderDao).execute(order);
    }

    public void deleteSingleOrder (Order order) {
        new deleteSingleAsyncTask(mOrderDao).execute(order);
    }

    private static class deleteSingleAsyncTask extends AsyncTask<Order, Void, Void> {

        private OrderDao mAsyncTaskDao;

        deleteSingleAsyncTask(OrderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Order... params) {
            mAsyncTaskDao.deleteOrder(params[0]);
            return null;
        }
    }

    public void deleteSingleOrder2 (Order order) {
        new deleteSingleAsyncTask2(mOrderDao).execute(order);
    }

    private static class deleteSingleAsyncTask2 extends AsyncTask<Order, Void, Void> {

        private OrderDao mAsyncTaskDao;

        deleteSingleAsyncTask2(OrderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Order... params) {
            mAsyncTaskDao.deleteOrder2(1);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private OrderDao mAsyncTaskDao;

        deleteAsyncTask(OrderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    private static class insertAsyncTask extends AsyncTask<Order, Void, Void> {

        private OrderDao mAsyncTaskDao;

        insertAsyncTask(OrderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Order... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}//OrderRepository
