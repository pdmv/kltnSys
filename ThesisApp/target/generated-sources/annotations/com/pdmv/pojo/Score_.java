package com.pdmv.pojo;

import com.pdmv.pojo.Council;
import com.pdmv.pojo.Criterion;
import com.pdmv.pojo.Lecturer;
import com.pdmv.pojo.Thesis;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-05-29T15:01:49")
@StaticMetamodel(Score.class)
public class Score_ { 

    public static volatile SingularAttribute<Score, Lecturer> lecturerId;
    public static volatile SingularAttribute<Score, Float> score;
    public static volatile SingularAttribute<Score, Criterion> criterionId;
    public static volatile SingularAttribute<Score, Council> councilId;
    public static volatile SingularAttribute<Score, Thesis> thesisId;
    public static volatile SingularAttribute<Score, Integer> id;

}