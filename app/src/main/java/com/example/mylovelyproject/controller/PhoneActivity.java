package com.example.mylovelyproject.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylovelyproject.GSMStat;
import com.example.mylovelyproject.R;
import com.example.mylovelyproject.controller.adapter.PhoneAdapter;
import com.example.mylovelyproject.model.entity.Model;
import com.example.mylovelyproject.model.entity.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneActivity extends AppCompatActivity {

    private long groupId;
    private String searchLastName;
    private GSMStat app;
    private ListView phoneListView;
    private PhoneAdapter phoneAdapter;
    private List<Phone> phones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        groupId = getIntent().getLongExtra("id", 0);
        searchLastName = getIntent().getStringExtra("searchLastname");

        if (getSupportActionBar() != null) {
            if (searchLastName == null) {
                getSupportActionBar().setTitle("Devices");
            } else {
                getSupportActionBar().setTitle("Find Device by FullModel: " + searchLastName);
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        app = (GSMStat) getApplication();
        phoneListView = findViewById(R.id.phoneListView);
        phoneListView.setEmptyView(findViewById(android.R.id.empty));

        new Thread(() -> {
            Model model = app.modelService.getGroup(groupId);
            if (model == null) {
                return;
            }
            runOnUiThread(() -> {
                phoneAdapter = new PhoneAdapter(this, phones, model);
                phoneListView.setAdapter(phoneAdapter);
            });
        }).start();

    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(() -> {
            List<Phone> phones;
            if (searchLastName == null) {
                phones = app.phoneService.getPhones(groupId);
            } else {
                phones = app.phoneService.searchStudentsByLastname(searchLastName, groupId);
            }

            runOnUiThread(() -> {
                phoneAdapter.clear();
                phoneAdapter.addAll(phones);
                phoneAdapter.notifyDataSetChanged();
            });
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getStringExtra("searchLastname") == null) {
            getMenuInflater().inflate(R.menu.menu_students, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_create_student:
                startActivity(new Intent(this, PhoneProfileActivity.class).putExtra("groupId", groupId));
                return true;
            case R.id.action_search_student:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                LinearLayout view = new LinearLayout(this);
                view.setOrientation(LinearLayout.VERTICAL);

                final EditText lastnameEditText = new EditText(this);
                lastnameEditText.setHint(R.string.phone_full_model);
                view.addView(lastnameEditText);
                builder.setView(view);

                builder.setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    startActivity(new Intent(this, PhoneActivity.class)
                            .putExtra("id", groupId)
                            .putExtra("searchLastname", lastnameEditText.getText().toString()));
                });

                builder.setNegativeButton(android.R.string.no, null);
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}