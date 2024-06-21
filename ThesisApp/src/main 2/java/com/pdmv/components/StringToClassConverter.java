/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.components;

import com.pdmv.pojo.Class;
import com.pdmv.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author phamdominhvuong
 */
@Component
public class StringToClassConverter implements Converter<String, Class> {
    @Autowired
    private ClassService classService;

    @Override
    public Class convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        try {
            int classId = Integer.parseInt(source);
            return classService.getClassById(classId);
        } catch (NumberFormatException e) {
            return null; // Hoặc xử lý lỗi theo cách khác
        }
    }
}
