package com.pdmv.pojo;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.Class;
import com.pdmv.pojo.Faculty;
import com.pdmv.pojo.Major;
import com.pdmv.pojo.ThesisStudent;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Student.class)
public class Student_ { 

    public static volatile SingularAttribute<Student, String> lastName;
    public static volatile SingularAttribute<Student, String> address;
    public static volatile SingularAttribute<Student, Major> majorId;
    public static volatile SingularAttribute<Student, String> gender;
    public static volatile SingularAttribute<Student, Boolean> active;
    public static volatile SetAttribute<Student, ThesisStudent> thesisStudentSet;
    public static volatile SingularAttribute<Student, Date> updatedDate;
    public static volatile SingularAttribute<Student, String> firstName;
    public static volatile SingularAttribute<Student, Account> accountId;
    public static volatile SingularAttribute<Student, Faculty> facultyId;
    public static volatile SingularAttribute<Student, Class> classId;
    public static volatile SingularAttribute<Student, Date> createdDate;
    public static volatile SingularAttribute<Student, Date> dob;
    public static volatile SingularAttribute<Student, Integer> id;
    public static volatile SingularAttribute<Student, String> email;

}