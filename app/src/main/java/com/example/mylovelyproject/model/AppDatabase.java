package com.example.mylovelyproject.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mylovelyproject.model.dao.ModelDao;
import com.example.mylovelyproject.model.dao.PhoneDao;
import com.example.mylovelyproject.model.entity.Model;
import com.example.mylovelyproject.model.entity.Phone;

@Database(entities = {Model.class, Phone.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ModelDao groupDao();
    public abstract PhoneDao studentDao();
}
