/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.repositories.impl;

import com.pdmv.dto.thesis.ThesisDTO;
import com.pdmv.dto.thesis.CreateThesisDTO;
import com.pdmv.dto.thesis.ThesisDetailsDTO;
import com.pdmv.dto.thesis.ThesisLecturerDTO;
import com.pdmv.dto.thesis.ThesisStudentDTO;
import com.pdmv.pojo.Affair;
import com.pdmv.pojo.Faculty;
import com.pdmv.pojo.Lecturer;
import com.pdmv.pojo.Major;
import com.pdmv.pojo.SchoolYear;
import com.pdmv.pojo.Student;
import com.pdmv.pojo.Thesis;
import com.pdmv.pojo.ThesisLecturer;
import com.pdmv.pojo.ThesisStudent;
import com.pdmv.repositories.AffairRepository;
import com.pdmv.repositories.FacultyRepository;
import com.pdmv.repositories.LecturerRepository;
import com.pdmv.repositories.MajorRepositoty;
import com.pdmv.repositories.SchoolYearRepository;
import com.pdmv.repositories.StudentRepository;
import com.pdmv.repositories.ThesisRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phamdominhvuong
 */
@Repository
@Transactional
@PropertySource("classpath:theses.properties")
@PropertySource("classpath:pagination.properties")
public class ThesisRepositoryImpl implements ThesisRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private SchoolYearRepository schoolYearRepo;
    @Autowired
    private FacultyRepository facultyRepo;
    @Autowired
    private MajorRepositoty majorRepo;
    @Autowired
    private LecturerRepository lecturerRepo;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private AffairRepository affairRepo;
    @Autowired
    private Environment env;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int addOrUpdate(CreateThesisDTO thesisDTO) {
        Session s = this.factory.getObject().getCurrentSession();
        Thesis thesis = new Thesis();

        populateThesis(thesisDTO, thesis);

        if (thesisDTO.getId() == null) {
            s.save(thesis);
            saveThesisLecturers(thesisDTO, thesis, s);
            saveThesisStudents(thesisDTO, thesis, s);
        } else {
            s.merge(thesis);
            updateThesisLecturers(thesisDTO, thesis, s);
            updateThesisStudents(thesisDTO, thesis, s);
        }
        
        return thesis.getId();
    }

    private void populateThesis(CreateThesisDTO thesisDTO, Thesis thesis) {
        thesis.setId(thesisDTO.getId());
        thesis.setName(thesisDTO.getName());
        thesis.setReportFile(thesisDTO.getReportFile());
        thesis.setStartDate(thesisDTO.getStartDate());
        thesis.setEndDate(thesisDTO.getEndDate());
        thesis.setExpDate(thesisDTO.getExpDate());
        thesis.setAvgScore(thesisDTO.getAvgScore());
        thesis.setComment(thesisDTO.getComment());
        
        if (thesis.getId() == null && thesisDTO.getStatus() == null) {
            thesis.setStatus(this.env.getProperty("thesis.status.in_progress"));
        } else {
            thesis.setStatus(thesisDTO.getStatus());
        }
        
        thesis.setCreatedDate(thesisDTO.getCreatedDate());
        thesis.setActive(thesisDTO.getActive());

        SchoolYear schoolYear = schoolYearRepo.getSchoolYearById(thesisDTO.getSchoolYearId());
        Faculty faculty = facultyRepo.getFacultyById(thesisDTO.getFacultyId());
        Major major = majorRepo.getMajorById(thesisDTO.getMajorId());
        Lecturer criticalLecturer = lecturerRepo.getLecturerById(thesisDTO.getCriticalLecturerId());
        Affair affair = affairRepo.getAffairById(thesisDTO.getAffairId());

        thesis.setSchoolYearId(schoolYear);
        thesis.setFacultyId(faculty);
        thesis.setMajorId(major);
        thesis.setCriticalLecturerId(criticalLecturer);
        thesis.setAffairId(affair);
    }

    private void saveThesisLecturers(CreateThesisDTO thesisDTO, Thesis thesis, Session s) {
        for (ThesisLecturerDTO lecturerDTO : thesisDTO.getThesisLecturerSet()) {
            Lecturer l = lecturerRepo.getLecturerById(lecturerDTO.getLecturerId());
            if (l == null) {
                throw new IllegalArgumentException("Giảng viên không tồn tại!");
            }
            validateFaculty(l, thesis.getAffairId());
            ThesisLecturer lecturer = new ThesisLecturer();
            lecturer.setThesisId(thesis);
            lecturer.setLecturerId(l);
            s.persist(lecturer);
        }
    }

    private void saveThesisStudents(CreateThesisDTO thesisDTO, Thesis thesis, Session s) {
        for (ThesisStudentDTO studentDTO : thesisDTO.getThesisStudentSet()) {
            Student stu = studentRepo.getStudentById(studentDTO.getStudentId());
            if (stu == null) {
                throw new IllegalArgumentException("Sinh viên không tồn tại!");
            }
            validateFaculty(stu, thesis.getAffairId());
            ThesisStudent student = new ThesisStudent();
            student.setThesisId(thesis);
            student.setStudentId(stu);
            s.persist(student);
        }
    }

    private void updateThesisLecturers(CreateThesisDTO thesisDTO, Thesis thesis, Session s) {
        List<ThesisLecturer> existingLecturers = s.createQuery("FROM ThesisLecturer WHERE thesisId.id = :thesisId", ThesisLecturer.class)
                .setParameter("thesisId", thesis.getId())
                .getResultList();
        updateThesisAssociations(existingLecturers, thesisDTO.getThesisLecturerSet(), thesis, s, ThesisLecturer.class);
    }

    private void updateThesisStudents(CreateThesisDTO thesisDTO, Thesis thesis, Session s) {
        List<ThesisStudent> existingStudents = s.createQuery("FROM ThesisStudent WHERE thesisId.id = :thesisId", ThesisStudent.class)
                .setParameter("thesisId", thesis.getId())
                .getResultList();
        updateThesisAssociations(existingStudents, thesisDTO.getThesisStudentSet(), thesis, s, ThesisStudent.class);
    }

    private <T, D> void updateThesisAssociations(List<T> existingAssociations, Set<D> newAssociations, Thesis thesis, Session s, Class<T> associationClass) {
        Set<Integer> existingIds = existingAssociations.stream()
                .map(assoc -> getIdFromAssociation(assoc))
                .collect(Collectors.toSet());
        Set<Integer> newIds = newAssociations.stream()
                .map(dto -> getIdFromDTO(dto))
                .collect(Collectors.toSet());

        Set<Integer> idsToAdd = newIds.stream()
                .filter(id -> !existingIds.contains(id))
                .collect(Collectors.toSet());

        Set<Integer> idsToRemove = existingIds.stream()
                .filter(id -> !newIds.contains(id))
                .collect(Collectors.toSet());

        idsToAdd.forEach(id -> {
            T association = createAssociationFromDTO(id, thesis, associationClass);
            s.persist(association);
        });

        idsToRemove.forEach(id -> {
            T associationToDelete = existingAssociations.stream()
                    .filter(assoc -> Objects.equals(getIdFromAssociation(assoc), id))
                    .findFirst()
                    .orElse(null);
            if (associationToDelete != null) {
                s.delete(associationToDelete);
            }
        });
    }

    private <T> Integer getIdFromAssociation(T association) {
        if (association instanceof ThesisLecturer) {
            return ((ThesisLecturer) association).getLecturerId().getId();
        } else if (association instanceof ThesisStudent) {
            return ((ThesisStudent) association).getStudentId().getId();
        }
        return null;
    }

    private <D> Integer getIdFromDTO(D dto) {
        if (dto instanceof ThesisLecturerDTO) {
            return ((ThesisLecturerDTO) dto).getLecturerId();
        } else if (dto instanceof ThesisStudentDTO) {
            return ((ThesisStudentDTO) dto).getStudentId();
        }
        return null;
    }

    private <T> T createAssociationFromDTO(Integer id, Thesis thesis, Class<T> associationClass) {
        if (associationClass == ThesisLecturer.class) {
            Lecturer lecturer = lecturerRepo.getLecturerById(id);
            ThesisLecturer thesisLecturer = new ThesisLecturer();
            thesisLecturer.setThesisId(thesis);
            thesisLecturer.setLecturerId(lecturer);
            return associationClass.cast(thesisLecturer);
        } else if (associationClass == ThesisStudent.class) {
            Student student = studentRepo.getStudentById(id);
            ThesisStudent thesisStudent = new ThesisStudent();
            thesisStudent.setThesisId(thesis);
            thesisStudent.setStudentId(student);
            return associationClass.cast(thesisStudent);
        }
        return null;
    }

    @Override
    public ThesisDetailsDTO getThesisDTOById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Thesis thesis = s.get(Thesis.class, id);

        if (thesis == null) {
            return null;
        }

        return ThesisDetailsDTO.toThesisDetailsDTO(thesis);
    }

    @Override
    public List<ThesisDTO> getLists(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Thesis> query = builder.createQuery(Thesis.class);
        Root<Thesis> root = query.from(Thesis.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params != null) {
            String facultyId = params.get("facultyId");
            if (facultyId != null && !facultyId.isEmpty()) {
                try {
                    int id = Integer.parseInt(facultyId);
                    predicates.add(builder.equal(root.get("facultyId").get("id"), id));
                } catch (NumberFormatException e) {
                    System.err.println(e.getMessage());
                }
            }
            
            String schoolYearId = params.get("schoolYearId");
            if (schoolYearId != null && !schoolYearId.isEmpty()) {
                try {
                    int id = Integer.parseInt(schoolYearId);
                    predicates.add(builder.equal(root.get("schoolYearId").get("id"), id));
                } catch (NumberFormatException e) {
                    System.err.println(e.getMessage());
                }
            }

            String name = params.get("name");
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }
            
            String status = params.get("status");
            if (status != null && !status.isEmpty()) {
                predicates.add(builder.like(root.get("status"), status));
            }

            String studentId = params.get("studentId");
            if (studentId != null && !studentId.isEmpty()) {
                try {
                    int id = Integer.parseInt(studentId);
                    Join<Thesis, ThesisStudent> thesisStudentJoin = root.join("thesisStudentSet");
                    predicates.add(builder.equal(thesisStudentJoin.get("studentId").get("id"), id));
                } catch (NumberFormatException e) {
                    System.err.println(e.getMessage());
                }
            }
            
            String lecturerId = params.get("lecturerId");
            if (lecturerId != null && !lecturerId.isEmpty()) {
                try {
                    int id = Integer.parseInt(lecturerId);
                    Join<Thesis, ThesisLecturer> thesisLecturerJoin = root.join("thesisLecturerSet");
                    predicates.add(builder.equal(thesisLecturerJoin.get("lecturerId").get("id"), id));
                } catch (NumberFormatException e) {
                    System.err.println(e.getMessage());
                }
            }
            
            String criticalLecturerId = params.get("criticalLecturerId");
            if (criticalLecturerId != null && !criticalLecturerId.isEmpty()) {
                try {
                    int id = Integer.parseInt(criticalLecturerId);
                    predicates.add(builder.equal(root.get("criticalLecturerId").get("id"), id));
                } catch (NumberFormatException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
        
        query.orderBy(builder.desc(root.get("id")));

        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", this.env.getProperty("pageSize"))); 

        Query<Thesis> q = s.createQuery(query);
        q.setFirstResult((page - 1) * pageSize); 
        q.setMaxResults(pageSize); 

        return q.getResultList().stream().map(ThesisDTO::toThesisDTO).collect(Collectors.toList());
    }

    private void validateFaculty(Object entity, Affair affair) {
        if (entity instanceof Lecturer) {
            Lecturer lecturer = (Lecturer) entity;
            if (!Objects.equals(lecturer.getFacultyId().getId(), affair.getFacultyId().getId())) {
                throw new IllegalArgumentException("Giảng viên không thuộc chung khoa với giáo vụ.");
            }
        } else if (entity instanceof Student) {
            Student student = (Student) entity;
            if (!Objects.equals(student.getFacultyId().getId(), affair.getFacultyId().getId())) {
                throw new IllegalArgumentException("Sinh viên không thuộc chung khoa với giáo vụ.");
            }
        }
    }

    @Override
    public void submitReportFile(Integer id, String url) {
        Session s = this.factory.getObject().getCurrentSession();
        Thesis thesis = s.get(Thesis.class, id);

        if (thesis == null) {
            throw new IllegalArgumentException("Không tìm thấy khoá luân!"); 
        }
        
        thesis.setReportFile(url);
        thesis.setStatus(this.env.getProperty("thesis.status.submitted"));
        s.merge(thesis);
    }

    @Override
    public Thesis getThesisById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Thesis thesis = s.get(Thesis.class, id);

        if (thesis == null) {
            return null;
        }
        
        return thesis;
    }
}
