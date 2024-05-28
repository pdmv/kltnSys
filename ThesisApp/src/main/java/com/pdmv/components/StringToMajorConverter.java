/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.components;

import com.pdmv.pojo.Major;
import com.pdmv.services.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author phamdominhvuong
 */
@Component
public class StringToMajorConverter implements Converter<String, Major> {

    @Autowired
    private MajorService majorService;

    @Override
    public Major convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        try {
            int majorId = Integer.parseInt(source);
            return majorService.getMajorById(majorId);
        } catch (NumberFormatException e) {
            return null; // Hoặc xử lý lỗi theo cách khác
        }
    }
}