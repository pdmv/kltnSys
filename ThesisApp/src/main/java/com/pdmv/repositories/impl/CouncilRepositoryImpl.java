package com.pdmv.repositories.impl;

import com.pdmv.dto.council.CouncilCriterionDTO;
import com.pdmv.dto.council.CouncilLecturerDTO;
import com.pdmv.dto.council.CouncilThesisDTO;
import com.pdmv.dto.council.CreateCouncilDTO;
import com.pdmv.dto.council.MarkDTO;
import com.pdmv.dto.council.ScoreDTO;
import com.pdmv.dto.council.SimpleCouncilDTO;
import com.pdmv.dto.report.CriterionScore;
import com.pdmv.dto.report.LecturerScore;
import com.pdmv.pojo.Council;
import com.pdmv.pojo.CouncilCriterion;
import com.pdmv.pojo.CouncilLecturer;
import com.pdmv.pojo.CouncilThesis;
import com.pdmv.pojo.Criterion;
import com.pdmv.pojo.Lecturer;
import com.pdmv.pojo.SchoolYear;
import com.pdmv.pojo.Score;
import com.pdmv.pojo.Thesis;
import com.pdmv.repositories.CouncilRepository;
import com.pdmv.repositories.CriterionRepository;
import com.pdmv.repositories.LecturerRepository;
import com.pdmv.repositories.SchoolYearRepository;
import com.pdmv.repositories.ThesisRepository;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phamdominhvuong
 */
@Repository
@Transactional
@PropertySource("classpath:theses.properties")
@PropertySource("classpath:pagination.properties")
public class CouncilRepositoryImpl implements CouncilRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private LecturerRepository lecturerRepo;
    @Autowired
    private ThesisRepository thesisRepo;
    @Autowired
    private SchoolYearRepository schoolYearRepo;
    @Autowired
    private CriterionRepository criterionRepo;
    @Autowired
    private Environment env;

    @Override
    public int addCouncil(CreateCouncilDTO council) {
        Session s = this.factory.getObject().getCurrentSession();
        Council result = this.parseCouncil(council);

        // Kiểm tra tổng trọng số
        float totalWeight = council.getCriterions().stream()
                .map(CouncilCriterionDTO::getWeight)
                .reduce(0f, Float::sum);

        if (totalWeight != 100) {
            throw new IllegalArgumentException("Tổng trọng số phải bằng 100.");
        }

        result.setStatus("pending");
        s.save(result);

        for (CouncilCriterionDTO dto : council.getCriterions()) {
            CouncilCriterion c = this.parseCouncilCriterion(dto);
            c.setCouncilId(result);
            s.save(c);
        }

        for (CouncilLecturerDTO dto : council.getLecturers()) {
            CouncilLecturer l = this.parseCouncilLecturer(dto);
            l.setCouncilId(result);
            s.save(l);
        }

        for (CouncilThesisDTO dto : council.getTheses()) {
            CouncilThesis t = this.parseCouncilThesis(dto, s);
            t.setCouncilId(result);
            s.save(t);
        }

        return result.getId();
    }

    private Council parseCouncil(CreateCouncilDTO c) {
        Council result = new Council();

        result.setName(c.getName());
        result.setMeetingDate(c.getMeetingDate());
        result.setAffairId(c.getAffairId());
        result.setFacultyId(c.getFacultyId());

        SchoolYear s = this.schoolYearRepo.getSchoolYearById(c.getSchoolYearId());
        result.setSchoolYearId(s);

        return result;
    }

    private CouncilCriterion parseCouncilCriterion(CouncilCriterionDTO dto) {
        CouncilCriterion result = new CouncilCriterion();

        result.setWeight(dto.getWeight());

        Criterion c = this.criterionRepo.getCriterionById(dto.getId());
        result.setCriterionId(c);

        return result;
    }

    private CouncilLecturer parseCouncilLecturer(CouncilLecturerDTO dto) {
        CouncilLecturer result = new CouncilLecturer();

        result.setPosition(dto.getPosition());

        Lecturer l = this.lecturerRepo.getLecturerById(dto.getId());
        result.setLecturerId(l);

        return result;
    }

    private CouncilThesis parseCouncilThesis(CouncilThesisDTO dto, Session s) {
        CouncilThesis result = new CouncilThesis();

        Thesis t = this.thesisRepo.getThesisById(dto.getId());
        result.setThesisId(t);
        t.setStatus(this.env.getProperty("thesis.status.under_review"));

        return result;
    }

    @Override
    public List<SimpleCouncilDTO> getLists(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Council> query = builder.createQuery(Council.class);
        Root<Council> root = query.from(Council.class);

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

            String name = params.get("name");
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }

            String thesisId = params.get("thesisId");
            if (thesisId != null && !thesisId.isEmpty()) {
                try {
                    int id = Integer.parseInt(thesisId);
                    Join<Council, CouncilThesis> councilThesisJoin = root.join("councilThesisSet");
                    predicates.add(builder.equal(councilThesisJoin.get("thesisId").get("id"), id));
                } catch (NumberFormatException e) {
                    System.err.println(e.getMessage());
                }
            }

            String lecturerId = params.get("lecturerId");
            if (lecturerId != null && !lecturerId.isEmpty()) {
                try {
                    int id = Integer.parseInt(lecturerId);
                    Join<Council, CouncilLecturer> councilLecturerJoin = root.join("councilLecturerSet");
                    predicates.add(builder.equal(councilLecturerJoin.get("lecturerId").get("id"), id));
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

            String status = params.get("status");
            if (status != null && !status.isEmpty()) {
                predicates.add(builder.like(root.get("status"), status));
            }
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
        
        query.orderBy(builder.desc(root.get("id")));

        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", this.env.getProperty("pageSize"))); 

        Query<Council> q = s.createQuery(query);
        q.setFirstResult((page - 1) * pageSize); 
        q.setMaxResults(pageSize);
        
        return q.getResultList().stream().map(SimpleCouncilDTO::toSimpleCouncilDTO).collect(Collectors.toList());
    }

    @Override
    public Council getCouncilById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Council.class, id);
    }

    @Override
    public void mark(MarkDTO markDTO) {
        Session session = this.factory.getObject().getCurrentSession();
        Council council = markDTO.getCouncilId();

        // Kiểm tra trạng thái hội đồng
        if ("blocked".equals(council.getStatus())) {
            throw new IllegalArgumentException("Hội đồng đã bị khóa, không thể chấm điểm.");
        }

        boolean isLecturerInCouncil = council.getCouncilLecturerSet().stream()
                .anyMatch(cl -> Objects.equals(cl.getLecturerId().getId(), markDTO.getLecturerId().getId()));

        if (!isLecturerInCouncil) {
            throw new IllegalArgumentException("Giảng viên không thuộc hội đồng này.");
        }

        // Kiểm tra xem đã có điểm chấm hay chưa
        Query<Long> checkExistingQuery = session.createQuery("SELECT COUNT(*) FROM Score WHERE councilId = :council AND thesisId = :thesis AND lecturerId = :lecturer", Long.class);
        checkExistingQuery.setParameter("council", markDTO.getCouncilId());
        checkExistingQuery.setParameter("thesis", this.thesisRepo.getThesisById(markDTO.getThesisId()));
        checkExistingQuery.setParameter("lecturer", markDTO.getLecturerId());
        boolean hasExistingScores = checkExistingQuery.getSingleResult() > 0;

        if (hasExistingScores) {
            // Đã có điểm chấm, cập nhật điểm
            updateScores(markDTO, session);
        } else {
            // Chưa có điểm chấm, thêm điểm mới
            insertScores(markDTO, session);
        }
    }

    private void insertScores(MarkDTO markDTO, Session session) {
        for (ScoreDTO dto : markDTO.getScores()) {
            Score score = new Score();
            Criterion c = this.criterionRepo.getCriterionById(dto.getId());

            score.setCouncilId(markDTO.getCouncilId());
            score.setLecturerId(markDTO.getLecturerId());
            score.setThesisId(this.thesisRepo.getThesisById(markDTO.getThesisId()));
            score.setCriterionId(c);
            score.setScore(dto.getScore());

            session.save(score);
        }
    }

    private void updateScores(MarkDTO markDTO, Session session) {
        // Lấy danh sách Score cũ
        Query<Score> existingScoresQuery = session.createQuery("FROM Score WHERE councilId = :council AND thesisId = :thesis AND lecturerId = :lecturer", Score.class);
        existingScoresQuery.setParameter("council", markDTO.getCouncilId());
        existingScoresQuery.setParameter("thesis", this.thesisRepo.getThesisById(markDTO.getThesisId()));
        existingScoresQuery.setParameter("lecturer", markDTO.getLecturerId());
        List<Score> existingScores = existingScoresQuery.getResultList();

        // Tạo Map chứa Score cũ theo criterionId
        Map<Integer, Score> existingScoresMap = existingScores.stream()
                .collect(Collectors.toMap(score -> score.getCriterionId().getId(), score -> score));

        // Cập nhật Score
        for (ScoreDTO scoreDTO : markDTO.getScores()) {
            Score existingScore = existingScoresMap.get(scoreDTO.getId());
            if (existingScore != null) {
                existingScore.setScore(scoreDTO.getScore());
                session.merge(existingScore);
            }
        }
    }

    private Score parseScore(MarkDTO mark) {
        Score result = new Score();

        Thesis t = this.thesisRepo.getThesisById(mark.getThesisId());

        result.setCouncilId(mark.getCouncilId());
        result.setLecturerId(mark.getLecturerId());
        result.setThesisId(t);

        return result;
    }

    @Override
    public MarkDTO getMarks(int councilId, int thesisId, int lecturerId) {
        Session session = this.factory.getObject().getCurrentSession();

        Council council = this.getCouncilById(councilId);
        Thesis thesis = this.thesisRepo.getThesisById(thesisId);
        Lecturer lecturer = this.lecturerRepo.getLecturerById(lecturerId);

        if (council == null || thesis == null || lecturer == null) {
            return null;
        }

        Query<Score> query = session.createQuery("FROM Score WHERE councilId = :council AND thesisId = :thesis AND lecturerId = :lecturer", Score.class);
        query.setParameter("council", council);
        query.setParameter("thesis", thesis);
        query.setParameter("lecturer", lecturer);

        Set<Score> scores = new HashSet<>(query.getResultList());

        MarkDTO markDTO = new MarkDTO();
        markDTO.setCouncilId(council);
        markDTO.setThesisId(thesisId);
        markDTO.setLecturerId(lecturer);
        markDTO.setScores(scores.stream().map(ScoreDTO::toScoreDTO).collect(Collectors.toSet()));

        return markDTO;
    }

    @Override
    public void blockCouncil(int councilId) {
        Session session = this.factory.getObject().getCurrentSession();
        Council council = session.get(Council.class, councilId);

        if (council != null) {
            council.setStatus("blocked");
            this.calculateAvgScore(session, councilId);
            session.update(council);
        } else {
            throw new IllegalArgumentException("Council not found.");
        }
    }
    
    @Override
    public void unblockCouncil(int councilId) {
        Session session = this.factory.getObject().getCurrentSession();
        Council council = session.get(Council.class, councilId);

        if (council != null) {
            council.setStatus("pending");
            session.update(council);
        } else {
            throw new IllegalArgumentException("Council not found.");
        }
    }

    public void calculateAvgScore(Session session, int councilId) {
        // Lấy danh sách CouncilCriterion
        Query<CouncilCriterion> councilCriterionQuery = session.createQuery("FROM CouncilCriterion WHERE councilId.id = :councilId", CouncilCriterion.class);
        councilCriterionQuery.setParameter("councilId", councilId);
        List<CouncilCriterion> councilCriteria = councilCriterionQuery.getResultList();

        // Tạo Map chứa trọng số của từng Criterion dựa vào CouncilCriterion
        Map<Integer, Float> criterionWeights = councilCriteria.stream()
                .collect(Collectors.toMap(cc -> cc.getCriterionId().getId(), CouncilCriterion::getWeight));

        // Lấy tất cả Score của Council, group by thesisId
        Query<Object[]> scoreQuery = session.createQuery("SELECT s.thesisId.id, s.score, s.criterionId.id FROM Score s WHERE s.councilId.id = :councilId", Object[].class);
        scoreQuery.setParameter("councilId", councilId);
        List<Object[]> scores = scoreQuery.getResultList();

        // Tính điểm trung bình cho mỗi Thesis
        scores.stream()
                .collect(Collectors.groupingBy(s -> (Integer) s[0], Collectors.toList()))
                .forEach((thesisId, thesisScores) -> {
                    // Tính tổng điểm có trọng số cho thesisId
                    double weightedSum = thesisScores.stream()
                            .mapToDouble(scoreData -> ((Number) scoreData[1]).doubleValue() * criterionWeights.getOrDefault((Integer) scoreData[2], 0.0f))
                            .sum();

                    // Tính điểm trung bình
                    double avgScore = weightedSum / criterionWeights.values().stream().mapToDouble(Float::doubleValue).sum();

                    // Cập nhật avgScore và status cho Thesis
                    Thesis thesis = this.thesisRepo.getThesisById(thesisId);
                    thesis.setAvgScore((float) avgScore);
                    thesis.setStatus("defended");
                    session.update(thesis);
                });
    }
    
    @Override
    public List<CriterionScore> getCriterionScoresByThesisId(int thesisId) {
        Session session = this.factory.getObject().getCurrentSession();

        // Lấy danh sách tiêu chí của hội đồng đã chấm cho khóa luận
        Query<Criterion> criterionQuery = session.createQuery(
                "SELECT DISTINCT cc.criterionId FROM CouncilCriterion cc " +
                "JOIN CouncilThesis ct ON cc.councilId.id = ct.councilId.id " +
                "WHERE ct.thesisId.id = :thesisId", Criterion.class);
        criterionQuery.setParameter("thesisId", thesisId);
        List<Criterion> criteria = criterionQuery.getResultList();

        List<CriterionScore> criterionScores = new ArrayList<>();

        for (Criterion criterion : criteria) {
            CriterionScore criterionScore = new CriterionScore();
            criterionScore.setCriterionName(criterion.getName());

            // Lấy danh sách điểm của từng giảng viên cho tiêu chí hiện tại
            Query<Object[]> scoreQuery = session.createQuery(
                    "SELECT l.lastName || ' ' || l.firstName, s.score " +
                    "FROM Score s " +
                    "JOIN Lecturer l ON s.lecturerId.id = l.id " +
                    "WHERE s.thesisId.id = :thesisId AND s.criterionId.id = :criterionId", Object[].class);
            scoreQuery.setParameter("thesisId", thesisId);
            scoreQuery.setParameter("criterionId", criterion.getId());
            List<Object[]> scores = scoreQuery.getResultList();

            Set<LecturerScore> lecturerScores = new HashSet<>();
            for (Object[] scoreData : scores) {
                LecturerScore lecturerScore = new LecturerScore();
                lecturerScore.setLecturerFullname((String) scoreData[0]);
                lecturerScore.setScore((float) scoreData[1]);
                lecturerScores.add(lecturerScore);
            }
            criterionScore.setScore(lecturerScores);

            criterionScores.add(criterionScore);
        }

        return criterionScores;
    }
}
