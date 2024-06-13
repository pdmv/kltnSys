package com.pdmv.pojo;

import com.pdmv.pojo.Council;
import com.pdmv.pojo.Criterion;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(CouncilCriterion.class)
public class CouncilCriterion_ { 

    public static volatile SingularAttribute<CouncilCriterion, Criterion> criterionId;
    public static volatile SingularAttribute<CouncilCriterion, Council> councilId;
    public static volatile SingularAttribute<CouncilCriterion, Float> weight;
    public static volatile SingularAttribute<CouncilCriterion, Integer> id;

}