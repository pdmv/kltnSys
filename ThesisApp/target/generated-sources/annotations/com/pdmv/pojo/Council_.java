package com.pdmv.pojo;

import com.pdmv.pojo.CouncilCriterion;
import com.pdmv.pojo.CouncilLecturer;
import com.pdmv.pojo.SchoolYear;
import com.pdmv.pojo.Score;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Council.class)
public class Council_ { 

    public static volatile SetAttribute<Council, CouncilLecturer> councilLecturerSet;
    public static volatile SingularAttribute<Council, Date> createdDate;
    public static volatile SingularAttribute<Council, String> name;
    public static volatile SingularAttribute<Council, Boolean> active;
    public static volatile SingularAttribute<Council, Integer> id;
    public static volatile SingularAttribute<Council, Date> updatedDate;
    public static volatile SetAttribute<Council, CouncilCriterion> councilCriterionSet;
    public static volatile SingularAttribute<Council, SchoolYear> schoolYearId;
    public static volatile SingularAttribute<Council, String> status;
    public static volatile SetAttribute<Council, Score> scoreSet;

}