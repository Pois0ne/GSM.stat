package com.example.mylovelyproject.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mylovelyproject.model.entity.Model;

import java.util.List;

@Dao
public interface ModelDao {

    @Query("select * from models order by id")
    List<Model> getModels();

    @Query("select * from models where id = :id")
    Model getModel(long id);

    @Insert
    long createModel(Model model);

    @Update
    void updateModel(Model model);

    @Delete
    int removeModel(Model model);

}
