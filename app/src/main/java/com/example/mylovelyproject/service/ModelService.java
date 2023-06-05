package com.example.mylovelyproject.service;

import com.example.mylovelyproject.model.AppDatabase;
import com.example.mylovelyproject.model.dao.ModelDao;
import com.example.mylovelyproject.model.dao.PhoneDao;
import com.example.mylovelyproject.model.entity.Model;

import java.util.List;

public class ModelService {

    private final ModelDao modelDao;

    private final PhoneDao phoneDao;

    public ModelService(AppDatabase database) {
        modelDao = database.groupDao();
        phoneDao = database.studentDao();
    }

    public List<Model> getGroups() {
        return modelDao.getModels();
    }

    public Model getGroup(long id) {
        return modelDao.getModel(id);
    }

    public Model createGroup(Model model) {
        long id = modelDao.createModel(model);
        return modelDao.getModel(id);
    }

    public Model editGroup(Model model) {
        modelDao.updateModel(model);
        return model;
    }

    public void deleteGroup(Model model) throws IllegalArgumentException {
        if (!phoneDao.getPhoneOfModel(model.getId()).isEmpty()) {
            throw new IllegalArgumentException("Can not delete model - there are phones inside");
        }
        if (modelDao.removeModel(model) != 1) {
            throw new IllegalArgumentException("Problemo");
        };
    }

}
