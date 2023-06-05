package com.example.mylovelyproject.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity(tableName = "phones")
public class Phone {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "imei")
    private String imei;

    @ColumnInfo(name = "fullModel")
    private String fullModel;

    @ColumnInfo(name = "faultType")
    private String faultType;

    @ColumnInfo(name = "date")
    private Long date;

    @ColumnInfo(name = "phone_id")
    private Long phoneId;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "spent")
    private String spent;

    public void setDateAsDate(LocalDate date) {
        if (date == null) {
            this.date = null;
            return;
        }
        this.date = date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }

    public LocalDate getDateAsDate() {
        if (date == null) {
            return null;
        }
        return Instant.ofEpochSecond(date).atZone(ZoneId.systemDefault()).toLocalDate();
    }




    // There is no god further. Only Getters and setters
    //why not lombok? Cuz its such a buggy shit!


    public Long getId() {
        return id;
    }

    public String getImei() {
        return imei;
    }

    public String getFullModel() {
        return fullModel;
    }

    public String getFaultType() {
        return faultType;
    }

    public Long getDate() {
        return date;
    }

    public Long getPhoneId() {
        return phoneId;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setFullModel(String fullModel) {
        this.fullModel = fullModel;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }


    public String  getPrice() {
        return price;
    }

    public String  getSpent() {
        return spent;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSpent(String spent) {
        this.spent = spent;
    }
}
