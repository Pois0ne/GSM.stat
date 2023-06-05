package com.example.mylovelyproject.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.mylovelyproject.GSMStat;
import com.example.mylovelyproject.R;
import com.example.mylovelyproject.model.entity.Model;
import com.example.mylovelyproject.service.ModelService;

public class ModelProfileDialogFragment extends DialogFragment {

    public static String TAG = "CreatePhoneDialogFragment";

    private ModelService modelService;

    private Model model = null;
    private Boolean isForEditing = false;

    public ModelProfileDialogFragment() {
        super();
    }

    public ModelProfileDialogFragment(Model model) {
        super();
        this.model = model;
        this.isForEditing = true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        modelService = ((GSMStat) (requireActivity().getApplication())).modelService;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout view = new LinearLayout(getActivity());
        view.setOrientation(LinearLayout.VERTICAL);

        final EditText brandEditText = new EditText(getActivity());
        brandEditText.setHint(R.string.phone_brand);
        final EditText modelNameEditText = new EditText(getActivity());
        modelNameEditText.setHint(R.string.model_name);

        view.addView(brandEditText);
        view.addView(modelNameEditText);
        builder.setView(view);

        if (isForEditing) {
            brandEditText.setText(model.getBrand());
            modelNameEditText.setText(model.getModelName());

        }

        builder.setMessage(R.string.create_model)
                .setPositiveButton(R.string.action_ok, (dialog, id) -> {

                    if (!isForEditing) {
                        model = new Model();
                    }
                    //TODO: РАзобраться с phoneBrand
                    model.setBrand(brandEditText.getText().toString());
                    model.setModelName(modelNameEditText.getText().toString());

                    if (model.getBrand() == null || model.getBrand().length() == 0) {
                        return;
                    }

                    if (model.getModelName() == null || model.getModelName().length() == 0) {
                        return;
                    }

                    new Thread(() -> {

                        if (!isForEditing) {
                            modelService.createGroup(model);
                        } else {
                            modelService.editGroup(model);
                        }

                        getActivity().runOnUiThread(() -> {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), isForEditing ? "Model updated" : "Model created", Toast.LENGTH_SHORT).show();
                            if (getActivity() instanceof ModelActivity) {
                                ((ModelActivity) getActivity()).updateGroups();
                            }
                        });
                    }).start();

                })
                .setNegativeButton(R.string.action_cancel, (dialog, id) -> {
                    dialog.cancel();
                });

        return builder.create();
    }

}
