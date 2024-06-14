package com.pdmv.pojo;

import com.pdmv.pojo.Council;
import com.pdmv.pojo.Criterion;
import com.pdmv.pojo.Lecturer;
import com.pdmv.pojo.Thesis;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Score.class)
public class Score_ { 

    public static volatile SingularAttribute<Score, Lecturer> lecturerId;
    public static volatile SingularAttribute<Score, Float> score;
    public static volatile SingularAttribute<Score, Date> createdDate;
    public static volatile SingularAttribute<Score, Criterion> criterionId;
    public static volatile SingularAttribute<Score, Council> councilId;
    public static volatile SingularAttribute<Score, Thesis> thesisId;
    public static volatile SingularAttribute<Score, Boolean> active;
    public static volatile SingularAttribute<Score, Integer> id;
    public static volatile SingularAttribute<Score, Date> updatedDate;

}