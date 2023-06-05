package com.example.mylovelyproject.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mylovelyproject.model.entity.Phone;

import java.util.List;

@Dao
public interface PhoneDao {

    @Query("select * from phones order by id")
    List<Phone> getPhones();

    @Query("select * from phones where phone_id = :phoneId order by id")
    List<Phone> getPhoneOfModel(long phoneId);

    @Query("select * from phones where id = :id")
    Phone getPhone(long id);

    @Query("select * from phones where fullModel like :fullModel and phone_id = :phoneId")
    List<Phone> findPhoneByFullModel(String fullModel, long phoneId);

    @Insert
    long createPhone(Phone phone);

    @Update
    void updatePhone(Phone phone);

    @Delete
    int removePhone(Phone phone);
    
}
