package com.pdmv.pojo;

import com.pdmv.pojo.Council;
import com.pdmv.pojo.Thesis;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-05-27T16:57:32")
@StaticMetamodel(SchoolYear.class)
public class SchoolYear_ { 

    public static volatile SetAttribute<SchoolYear, Council> councilSet;
    public static volatile SingularAttribute<SchoolYear, Date> createdDate;
    public static volatile SingularAttribute<SchoolYear, Date> startYear;
    public static volatile SingularAttribute<SchoolYear, Boolean> active;
    public static volatile SetAttribute<SchoolYear, Thesis> thesisSet;
    public static volatile SingularAttribute<SchoolYear, Integer> id;
    public static volatile SingularAttribute<SchoolYear, Date> updatedDate;
    public static volatile SingularAttribute<SchoolYear, Date> endYear;

}