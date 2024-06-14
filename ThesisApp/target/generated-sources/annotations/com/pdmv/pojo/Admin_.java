package com.pdmv.pojo;

import com.pdmv.pojo.Account;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Admin.class)
public class Admin_ { 

    public static volatile SingularAttribute<Admin, String> lastName;
    public static volatile SingularAttribute<Admin, String> firstName;
    public static volatile SingularAttribute<Admin, Account> accountId;
    public static volatile SingularAttribute<Admin, String> address;
    public static volatile SingularAttribute<Admin, Date> createdDate;
    public static volatile SingularAttribute<Admin, String> gender;
    public static volatile SingularAttribute<Admin, Date> dob;
    public static volatile SingularAttribute<Admin, Boolean> active;
    public static volatile SingularAttribute<Admin, Integer> id;
    public static volatile SingularAttribute<Admin, Date> updatedDate;
    public static volatile SingularAttribute<Admin, String> email;

}