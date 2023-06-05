package com.example.mylovelyproject.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mylovelyproject.R;
import com.example.mylovelyproject.controller.PhoneProfileActivity;
import com.example.mylovelyproject.model.entity.Model;
import com.example.mylovelyproject.model.entity.Phone;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PhoneAdapter extends ArrayAdapter<Phone> {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final Model model;

    public PhoneAdapter(@NonNull Context context, @NonNull List<Phone> phones, @NonNull Model model) {
        super(context, R.layout.item_model, phones);
        this.model = model;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_phone, null);
        }

        TextView imeiTextView = convertView.findViewById(R.id.imeiTextView);
        TextView brandTextView = convertView.findViewById(R.id.brandTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        TextView fullModelTextView = convertView.findViewById(R.id.fullModelTextView);
        TextView FaultTypeEditTextTextMultiLine = convertView.findViewById((R.id.FaultTypeEditTextTextMultiLine));


        TextView PriceEditText = convertView.findViewById(R.id.PriceEditText);
        TextView SpentEditText = convertView.findViewById(R.id.SpentEditText);

        Phone phone = getItem(position);


        imeiTextView.setText(phone.getImei());
        fullModelTextView.setText(phone.getFullModel());
        FaultTypeEditTextTextMultiLine.setText(phone.getFaultType());
        brandTextView.setText(model.getBrand());
        dateTextView.setText(dateFormatter.format(phone.getDateAsDate()));

        //TODO: Тут происходят ошибки
        PriceEditText.setText(phone.getPrice());
        SpentEditText.setText(phone.getSpent());


        convertView.setOnClickListener((v) -> {
            getContext().startActivity(
                    new Intent(getContext(), PhoneProfileActivity.class).putExtra("id", phone.getId()).putExtra("groupId", phone.getPhoneId())
            );
        });


        return convertView;
    }
}
