package com.example.mylovelyproject.service;

import com.example.mylovelyproject.model.AppDatabase;
import com.example.mylovelyproject.model.dao.PhoneDao;
import com.example.mylovelyproject.model.entity.Phone;

import java.util.List;

public class PhoneService {

    private final PhoneDao phoneDao;

    public PhoneService(AppDatabase database) {
        phoneDao = database.studentDao();
    }

    public List<Phone> getPhones() {
        return phoneDao.getPhones();
    }

    public List<Phone> getPhones(long groupId) {
        return phoneDao.getPhoneOfModel(groupId);
    }

    public Phone getStudent(long studentId) {
        return phoneDao.getPhone(studentId);
    }

    public Phone createStudent(Phone Phone) {
        long id = phoneDao.createPhone(Phone);
        return phoneDao.getPhone(id);
    }

    public List<Phone> searchStudentsByLastname(String fullModel, long groupId) {
        return phoneDao.findPhoneByFullModel(fullModel, groupId);
    }

    public Phone editStudent(Phone Phone) {
        phoneDao.updatePhone(Phone);
        return Phone;
    }

    public void deletePhone(Phone phone) throws IllegalArgumentException {
        if (phoneDao.removePhone(phone) != 1) {
            throw new IllegalArgumentException("Problem");
        };
    }

}
