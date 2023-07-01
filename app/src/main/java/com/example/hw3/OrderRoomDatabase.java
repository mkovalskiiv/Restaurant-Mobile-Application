package com.example.hw3;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Order.class}, version = 2,  exportSchema = false)
public abstract class OrderRoomDatabase extends RoomDatabase {
    public abstract OrderDao orderDao();

    private static OrderRoomDatabase INSTANCE;

    static OrderRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OrderRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OrderRoomDatabase.class, "order_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(orderRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback orderRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            //Enable the line beflow if we would like to auto generate some students
            //when launching the program
            //new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final OrderDao mDao;
        int numberofOrders = 3;

        PopulateDbAsync(OrderRoomDatabase db) {
            mDao = db.orderDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            // mDao.deleteAll();

            for( int i = 0; i < numberofOrders; i++) {
                Order order = new Order();
                //Order order = new Order(999, "", "", "", "", "");
                order.setOrderName("Order Name"+i);
                order.setOrderEmail("Order Email"+i);
                order.setOrderPhone("Order Phone"+i);
                order.setOrderDate("Order Date"+i);
                order.setOrderAmount("Order Amount"+i);
                mDao.insert(order);
            }
            return null;
        }
    }
}//OrderRoomDatabase
