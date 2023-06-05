package com.example.mylovelyproject.controller;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylovelyproject.GSMStat;
import com.example.mylovelyproject.R;
import com.example.mylovelyproject.model.entity.Phone;
import com.example.mylovelyproject.service.PhoneService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PhoneProfileActivity extends AppCompatActivity {

    private final Calendar myCalendar = Calendar.getInstance();

    private Long groupId = null;
    private Long phoneId = null;
    private Phone phone = null;
    private boolean isForEditing = false;

    private EditText imeiEditText;
    private EditText fullModelEditText;
    private EditText FaultTypeEditText;
    private EditText dateEditText;
    private EditText spentEditText;
    private EditText priceEditText;


    private PhoneService phoneService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_info);

        phoneService = ((GSMStat) getApplication()).phoneService;

        groupId = getIntent().getLongExtra("groupId", -1);
        if (groupId == -1) {
            finish();
        }

        phoneId = getIntent().getLongExtra("id", -1);
        if (phoneId == -1) { phoneId = null; }
        isForEditing = phoneId != null;

        if (getSupportActionBar() != null) {
            if (!isForEditing) {
                getSupportActionBar().setTitle("Create device");
            } else {
                getSupportActionBar().setTitle("Edit device");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        imeiEditText = findViewById(R.id.imeiEditText);            //IMEI
        fullModelEditText = findViewById(R.id.fullModelEditText);  //FULL_MODEL
        FaultTypeEditText = findViewById(R.id.faultTypeEditText);  //FAULT_TYPE
        dateEditText = findViewById(R.id.dateEditText);            //DATE
        spentEditText = findViewById(R.id.SpentEditText);          //Spent
        priceEditText = findViewById(R.id.PriceEditText);          //Price



        Button saveButton = findViewById(R.id.savePhoneButton);
        ImageButton deleteButton = findViewById(R.id.deletePhoneButton);

        if (isForEditing) {
            new Thread(() -> {
                phone = phoneService.getStudent(phoneId);
                runOnUiThread(() -> {
                    imeiEditText.setText(phone.getImei());
                    fullModelEditText.setText(phone.getFullModel());
                    FaultTypeEditText.setText(phone.getFaultType());
                    priceEditText.setText(phone.getPrice());
                    spentEditText.setText(phone.getPrice());
                    setDate(phone.getDateAsDate());
                });
            }).start();
        }

        saveButton.setOnClickListener((v) -> {

            if (!isForEditing) {
                phone = new Phone();
            }
//TODO: Тут происходит магия.
            phone.setImei(imeiEditText.getText().toString());
            phone.setFullModel(fullModelEditText.getText().toString());
            phone.setFaultType(FaultTypeEditText.getText().toString());
            phone.setDateAsDate(getDate());

            phone.setPrice(priceEditText.getText().toString());
            phone.setSpent(spentEditText.getText().toString());

            if (phone.getImei() == null || phone.getImei().length() == 0) {
                return;
            }

            if (phone.getFullModel() == null || phone.getFullModel().length() == 0) {
                return;
            }

            if (phone.getDateAsDate() == null) {
                return;
            }


            new Thread(() -> {
                if (!isForEditing) {
                    phone.setPhoneId(groupId);
                    phone = phoneService.createStudent(phone);
                    phoneId = phone.getId();
                } else {
                    phoneService.editStudent(phone);
                }

                runOnUiThread(() -> {
                    finish();
                    if (!isForEditing) {
                        deleteButton.setVisibility(View.VISIBLE);
                        isForEditing = true;
                    }
                });

            }).start();
        });

        if (!isForEditing) {
            deleteButton.setVisibility(View.GONE);
        }

        deleteButton.setOnClickListener((v) -> {
            if (isForEditing) {
                new Thread(() -> {
                    try {
                        phoneService.deletePhone(phone);
                        runOnUiThread(this::finish);
                    } catch (IllegalArgumentException e) {
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Failed to delete phone", Toast.LENGTH_SHORT).show();
                        });
                    }
                }).start();
            }
        });

    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    private void setDate(LocalDate date) {
        if (date == null) {
            return;
        }
        Date d = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        dateEditText.setText(dateFormat.format(d));
    }

    private LocalDate getDate() {
        try {
            return dateFormat.parse(dateEditText.getText().toString()).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
