package com.example.mylovelyproject.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "models")
public class Model {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "faculty_name")
    private String modelName;



    //GETTER SETTER DOWN BELOW

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModelName() {
        return modelName;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
