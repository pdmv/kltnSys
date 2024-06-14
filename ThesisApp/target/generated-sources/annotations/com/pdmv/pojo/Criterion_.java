package com.pdmv.pojo;

import com.pdmv.pojo.Affair;
import com.pdmv.pojo.CouncilCriterion;
import com.pdmv.pojo.Score;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Criterion.class)
public class Criterion_ { 

    public static volatile SingularAttribute<Criterion, Affair> affairId;
    public static volatile SingularAttribute<Criterion, Date> createdDate;
    public static volatile SingularAttribute<Criterion, String> name;
    public static volatile SingularAttribute<Criterion, String> description;
    public static volatile SingularAttribute<Criterion, Boolean> active;
    public static volatile SingularAttribute<Criterion, Integer> id;
    public static volatile SingularAttribute<Criterion, Date> updatedDate;
    public static volatile SetAttribute<Criterion, CouncilCriterion> councilCriterionSet;
    public static volatile SetAttribute<Criterion, Score> scoreSet;

}