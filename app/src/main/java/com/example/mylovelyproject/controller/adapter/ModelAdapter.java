package com.example.mylovelyproject.controller.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylovelyproject.R;
import com.example.mylovelyproject.controller.ModelActivity;
import com.example.mylovelyproject.controller.ModelProfileDialogFragment;
import com.example.mylovelyproject.controller.PhoneActivity;
import com.example.mylovelyproject.model.entity.Model;

import java.util.List;

public class ModelAdapter extends ArrayAdapter<Model> {

    public ModelAdapter(@NonNull Context context, @NonNull List<Model> models) {
        super(context, R.layout.item_model, models);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_model, null);
        }

        TextView brandTextView = convertView.findViewById(R.id.PhoneBrandTextView);
        TextView modelTextView = convertView.findViewById(R.id.ModelNameTextView);

        Model model = getItem(position);

        brandTextView.setText(model.getBrand() != null ? model.getBrand() : "№ Unknown");
        modelTextView.setText(model.getModelName() != null ? model.getModelName() : "Faculty of Wonder");

        ImageButton editGroupButton = convertView.findViewById(R.id.editModelButton);
        editGroupButton.setOnClickListener((v) -> {
            new ModelProfileDialogFragment(model).show(
                    ((AppCompatActivity) getContext()).getSupportFragmentManager(), ModelProfileDialogFragment.TAG);
        });

        ImageButton deleteGroupButton = convertView.findViewById(R.id.deleteModelButton);
        deleteGroupButton.setOnClickListener((v) -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Delete model")
                    .setMessage("Do you really want to delete model?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        if (getContext() instanceof ModelActivity) {
                            ((ModelActivity) (getContext())).groupDeletionConfirmed(model);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });


        convertView.setOnClickListener((v) -> {
            getContext().startActivity(
                    new Intent(getContext(), PhoneActivity.class).putExtra("id", model.getId())
            );
        });

        return convertView;
    }
}
