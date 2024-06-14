package com.pdmv.pojo;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.Criterion;
import com.pdmv.pojo.Faculty;
import com.pdmv.pojo.Thesis;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Affair.class)
public class Affair_ { 

    public static volatile SingularAttribute<Affair, String> lastName;
    public static volatile SingularAttribute<Affair, String> address;
    public static volatile SingularAttribute<Affair, String> gender;
    public static volatile SetAttribute<Affair, Criterion> criterionSet;
    public static volatile SingularAttribute<Affair, Boolean> active;
    public static volatile SingularAttribute<Affair, Date> updatedDate;
    public static volatile SingularAttribute<Affair, String> firstName;
    public static volatile SingularAttribute<Affair, Account> accountId;
    public static volatile SingularAttribute<Affair, Faculty> facultyId;
    public static volatile SingularAttribute<Affair, Date> createdDate;
    public static volatile SingularAttribute<Affair, Date> dob;
    public static volatile SetAttribute<Affair, Thesis> thesisSet;
    public static volatile SingularAttribute<Affair, Integer> id;
    public static volatile SingularAttribute<Affair, String> email;

}