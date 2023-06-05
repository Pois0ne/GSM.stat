package com.example.mylovelyproject.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mylovelyproject.GSMStat;
import com.example.mylovelyproject.R;
import com.example.mylovelyproject.controller.adapter.ModelAdapter;
import com.example.mylovelyproject.model.entity.Model;

import java.util.ArrayList;
import java.util.List;

public class ModelActivity extends AppCompatActivity {

    private GSMStat app;
    private ListView groupListView;
    private ModelAdapter modelAdapter;
    private List<Model> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Models");
        }

        app = (GSMStat) getApplication();

        groupListView = findViewById(R.id.studentListView);
        groupListView.setEmptyView(findViewById(android.R.id.empty));

        modelAdapter = new ModelAdapter(this, models);
        groupListView.setAdapter(modelAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateGroups();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_group:
                new ModelProfileDialogFragment().show(
                        getSupportFragmentManager(), ModelProfileDialogFragment.TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateGroups() {
        new Thread(() -> {
            List<Model> models = app.modelService.getGroups();
            runOnUiThread(() -> {
                modelAdapter.clear();
                modelAdapter.addAll(models);
                modelAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    public void groupDeletionConfirmed(Model model) {
        new Thread(() -> {
            try {
                app.modelService.deleteGroup(model);
                runOnUiThread(this::updateGroups);
            } catch (IllegalArgumentException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Model contains phones", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

}