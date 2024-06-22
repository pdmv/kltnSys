package com.pdmv.pojo;

import com.pdmv.pojo.Faculty;
import com.pdmv.pojo.Major;
import com.pdmv.pojo.Student;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-23T00:26:21")
@StaticMetamodel(Class.class)
public class Class_ { 

    public static volatile SetAttribute<Class, Student> studentSet;
    public static volatile SingularAttribute<Class, Faculty> facultyId;
    public static volatile SingularAttribute<Class, Date> createdDate;
    public static volatile SingularAttribute<Class, Major> majorId;
    public static volatile SingularAttribute<Class, String> name;
    public static volatile SingularAttribute<Class, Boolean> active;
    public static volatile SingularAttribute<Class, Integer> id;
    public static volatile SingularAttribute<Class, Date> updatedDate;

}