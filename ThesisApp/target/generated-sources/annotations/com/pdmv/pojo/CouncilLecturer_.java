package com.pdmv.pojo;

import com.pdmv.pojo.Council;
import com.pdmv.pojo.Lecturer;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(CouncilLecturer.class)
public class CouncilLecturer_ { 

    public static volatile SingularAttribute<CouncilLecturer, Lecturer> lecturerId;
    public static volatile SingularAttribute<CouncilLecturer, Council> councilId;
    public static volatile SingularAttribute<CouncilLecturer, Integer> id;
    public static volatile SingularAttribute<CouncilLecturer, String> position;

}