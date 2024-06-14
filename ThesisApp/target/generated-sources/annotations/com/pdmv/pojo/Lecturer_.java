package com.pdmv.pojo;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.CouncilLecturer;
import com.pdmv.pojo.Faculty;
import com.pdmv.pojo.Score;
import com.pdmv.pojo.Thesis;
import com.pdmv.pojo.ThesisLecturer;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Lecturer.class)
public class Lecturer_ { 

    public static volatile SingularAttribute<Lecturer, String> lastName;
    public static volatile SingularAttribute<Lecturer, String> address;
    public static volatile SingularAttribute<Lecturer, String> gender;
    public static volatile SingularAttribute<Lecturer, String> degree;
    public static volatile SingularAttribute<Lecturer, Boolean> active;
    public static volatile SingularAttribute<Lecturer, Date> updatedDate;
    public static volatile SingularAttribute<Lecturer, String> firstName;
    public static volatile SetAttribute<Lecturer, CouncilLecturer> councilLecturerSet;
    public static volatile SingularAttribute<Lecturer, Account> accountId;
    public static volatile SingularAttribute<Lecturer, Faculty> facultyId;
    public static volatile SingularAttribute<Lecturer, Date> createdDate;
    public static volatile SingularAttribute<Lecturer, Date> dob;
    public static volatile SetAttribute<Lecturer, Thesis> thesisSet;
    public static volatile SingularAttribute<Lecturer, Integer> id;
    public static volatile SingularAttribute<Lecturer, String> email;
    public static volatile SetAttribute<Lecturer, ThesisLecturer> thesisLecturerSet;
    public static volatile SetAttribute<Lecturer, Score> scoreSet;

}