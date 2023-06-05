package com.example.mylovelyproject;

import android.app.Application;

import androidx.room.Room;

import com.example.mylovelyproject.model.AppDatabase;
import com.example.mylovelyproject.service.ModelService;
import com.example.mylovelyproject.service.PhoneService;

public class GSMStat extends Application {

    public AppDatabase db;

    public ModelService modelService;

    public PhoneService phoneService;

    @Override
    public void onCreate() {
        super.onCreate();

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-my-lovely").build();
        //TODO: Переименовать БД

        modelService = new ModelService(db);
        phoneService = new PhoneService(db);

    }
}
