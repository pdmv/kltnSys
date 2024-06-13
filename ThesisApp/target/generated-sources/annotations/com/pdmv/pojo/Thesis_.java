package com.pdmv.pojo;

import com.pdmv.pojo.Affair;
import com.pdmv.pojo.Lecturer;
import com.pdmv.pojo.SchoolYear;
import com.pdmv.pojo.Score;
import com.pdmv.pojo.ThesisLecturer;
import com.pdmv.pojo.ThesisStudent;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-06-13T19:51:27")
@StaticMetamodel(Thesis.class)
public class Thesis_ { 

    public static volatile SingularAttribute<Thesis, Affair> affairId;
    public static volatile SingularAttribute<Thesis, Lecturer> criticalLecturerId;
    public static volatile SingularAttribute<Thesis, Date> endDate;
    public static volatile SingularAttribute<Thesis, String> reportFile;
    public static volatile SingularAttribute<Thesis, Boolean> active;
    public static volatile SetAttribute<Thesis, ThesisStudent> thesisStudentSet;
    public static volatile SingularAttribute<Thesis, Date> updatedDate;
    public static volatile SingularAttribute<Thesis, SchoolYear> schoolYearId;
    public static volatile SingularAttribute<Thesis, Date> expDate;
    public static volatile SingularAttribute<Thesis, Date> createdDate;
    public static volatile SingularAttribute<Thesis, Float> avgScore;
    public static volatile SingularAttribute<Thesis, String> name;
    public static volatile SingularAttribute<Thesis, String> comment;
    public static volatile SingularAttribute<Thesis, Integer> id;
    public static volatile SingularAttribute<Thesis, Date> startDate;
    public static volatile SetAttribute<Thesis, ThesisLecturer> thesisLecturerSet;
    public static volatile SingularAttribute<Thesis, String> status;
    public static volatile SetAttribute<Thesis, Score> scoreSet;

}