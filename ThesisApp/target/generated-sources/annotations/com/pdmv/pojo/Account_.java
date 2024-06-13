package com.pdmv.pojo;

import com.pdmv.pojo.Admin;
import com.pdmv.pojo.Affair;
import com.pdmv.pojo.Lecturer;
import com.pdmv.pojo.Student;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, String> password;
    public static volatile SingularAttribute<Account, String> role;
    public static volatile SingularAttribute<Account, Date> createdDate;
    public static volatile SingularAttribute<Account, Student> student;
    public static volatile SingularAttribute<Account, Boolean> active;
    public static volatile SingularAttribute<Account, Admin> admin;
    public static volatile SingularAttribute<Account, Lecturer> lecturer;
    public static volatile SingularAttribute<Account, Affair> affair;
    public static volatile SingularAttribute<Account, String> avatar;
    public static volatile SingularAttribute<Account, Date> updatedDate;
    public static volatile SingularAttribute<Account, Integer> id;
    public static volatile SingularAttribute<Account, String> username;

}