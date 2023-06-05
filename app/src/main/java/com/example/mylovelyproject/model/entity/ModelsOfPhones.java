package com.example.mylovelyproject.model.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ModelsOfPhones {
    @Embedded
    public Model model;
    @Relation(
         parentColumn = "id",
         entityColumn = "model_id"
    )
    public List<Phone> phones;
}
