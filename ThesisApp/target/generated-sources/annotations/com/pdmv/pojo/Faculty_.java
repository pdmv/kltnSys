package com.pdmv.pojo;

import com.pdmv.pojo.Affair;
import com.pdmv.pojo.Class;
import com.pdmv.pojo.Lecturer;
import com.pdmv.pojo.Major;
import com.pdmv.pojo.Student;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Faculty.class)
public class Faculty_ { 

    public static volatile SetAttribute<Faculty, Student> studentSet;
    public static volatile SingularAttribute<Faculty, Date> createdDate;
    public static volatile SetAttribute<Faculty, Lecturer> lecturerSet;
    public static volatile SetAttribute<Faculty, Major> majorSet;
    public static volatile SingularAttribute<Faculty, String> name;
    public static volatile SingularAttribute<Faculty, Boolean> active;
    public static volatile SingularAttribute<Faculty, Integer> id;
    public static volatile SingularAttribute<Faculty, Date> updatedDate;
    public static volatile SetAttribute<Faculty, Affair> affairSet;
    public static volatile SetAttribute<Faculty, Class> classSet;

}