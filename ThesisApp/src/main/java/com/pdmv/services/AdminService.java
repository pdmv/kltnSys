/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services;

import com.pdmv.pojo.Admin;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdominhvuong
 */
public interface AdminService {
    void addOrUpdate(Admin admin);
    List<Admin> getAdmins(Map<String, String> params);
    Admin getAdminById(int id);
    Admin getAdminByAccountId(int id);
}
