package com.pdmv.pojo;

import com.pdmv.pojo.Class;
import com.pdmv.pojo.Faculty;
import com.pdmv.pojo.Student;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Major.class)
public class Major_ { 

    public static volatile SetAttribute<Major, Student> studentSet;
    public static volatile SingularAttribute<Major, Faculty> facultyId;
    public static volatile SingularAttribute<Major, Date> createdDate;
    public static volatile SingularAttribute<Major, String> name;
    public static volatile SingularAttribute<Major, Boolean> active;
    public static volatile SingularAttribute<Major, Integer> id;
    public static volatile SingularAttribute<Major, Date> updatedDate;
    public static volatile SetAttribute<Major, Class> classSet;

}