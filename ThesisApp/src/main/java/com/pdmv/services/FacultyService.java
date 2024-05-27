/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.pojo.Faculty;
import java.util.List;

/**
 *
 * @author phamdominhvuong
 */
public interface FacultyService {
    void addOrUpdate(Faculty faculty);
    Faculty getFacultyById(int id);
    List<Faculty> getFaculties(String kw);
}
