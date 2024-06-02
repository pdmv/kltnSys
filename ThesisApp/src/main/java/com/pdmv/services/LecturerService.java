/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.pojo.Lecturer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface LecturerService {
    void addOrUpdate(Lecturer lecturer);
    List<Lecturer> getLecturers(Map<String, String> params);
    Lecturer getLecturerById(int id);
    Lecturer getLecturerByAccountId(int id);
}
