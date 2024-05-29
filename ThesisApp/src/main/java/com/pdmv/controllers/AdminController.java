/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.controllers;

import com.pdmv.pojo.Account;
import com.pdmv.pojo.Admin;
import com.pdmv.pojo.Affair;
import com.pdmv.pojo.Faculty;
import com.pdmv.pojo.Major;
import com.pdmv.pojo.SchoolYear;
import com.pdmv.pojo.Class;
import com.pdmv.pojo.Lecturer;
import com.pdmv.pojo.Student;
import com.pdmv.services.AdminService;
import com.pdmv.services.AffairService;
import com.pdmv.services.ClassService;
import com.pdmv.services.FacultyService;
import com.pdmv.services.LecturerService;
import com.pdmv.services.MajorService;
import com.pdmv.services.SchoolYearService;
import com.pdmv.services.StudentService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author phamdominhvuong
 */
@Controller
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private SchoolYearService schoolYearSrevice;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private ClassService classService;
    @Autowired
    private AffairService affairSevice;
    @Autowired
    private LecturerService lecturerService;
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public String list(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("adminList", this.adminService.getAdmins(params));
        return "admins";
    }
    
    @GetMapping("/add")
    public String createView(Model model) {
        Admin admin = new Admin();
        admin.setAccountId(new Account());
        model.addAttribute("admin", admin);
        return "add-admin";
    }
    
    @PostMapping("/add")
    public String create(@ModelAttribute("admin") @Valid Admin admin, 
                      BindingResult result,
                      HttpServletRequest request,
                      Model model) {
        if (result.hasErrors()) {
            return "add-admin";
        }

        try {
            this.adminService.addOrUpdate(admin);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "add-admin";
        }
        
        return "redirect:/admins";
    }
    
    @GetMapping("/{id}")
    public String updateView(Model model, @PathVariable(value = "id") int id) {
        Admin admin = this.adminService.getAdminById(id);
        admin.getAccountId().setPassword(null);
        model.addAttribute(admin);
        return "update-admin";
    }
    
    @PostMapping("/{id}")
    public String update(@ModelAttribute("admin") @Valid Admin admin, 
                          BindingResult result,
                          HttpServletRequest request,
                          Model model) {
        if (result.hasErrors()) {
            System.err.println(result.getAllErrors());
            return "update-admin";
        }

        try {
            this.adminService.addOrUpdate(admin);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "update-admin";
        }

        return "redirect:/admins";
    }
    
    @GetMapping("/school-years")
    public String getSchoolYear(Model model) {
        model.addAttribute("schoolYears", this.schoolYearSrevice.getSchoolYears());
        return "school-years";
    }
    
    @GetMapping("/school-years/add")
    public String createSchoolYearView(Model model) {
        model.addAttribute("schoolYear", new SchoolYear());
        return "add-school-year";
    }
    
    @PostMapping("/school-years")
    public String createSchoolYear(@ModelAttribute("schoolYear") @Valid SchoolYear schoolYear, 
                                   BindingResult result, 
                                   Model model) {
        if (result.hasErrors()) {
            return "add-school-year"; 
        }

        try {
            this.schoolYearSrevice.addOrUpdate(schoolYear); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm niên khoá.");
            return "add-school-year";
        }

        return "redirect:/admins/school-years";
    }
    
    @GetMapping("/school-years/{id}")
    public String updateSchoolYearView(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("schoolYear", this.schoolYearSrevice.getSchoolYearById(id));
        return "update-school-year";
    }
    
    @PostMapping("/school-years/{id}")
    public String updateSchoolYear(@ModelAttribute("schoolYear") @Valid SchoolYear schoolYear, 
                                   BindingResult result, 
                                   Model model) {
        if (result.hasErrors()) {
            return "update-school-year"; 
        }

        try {
            this.schoolYearSrevice.addOrUpdate(schoolYear); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật niên khoá.");
            return "update-school-year";
        }

        return "redirect:/admins/school-years";
    }
    
    @GetMapping("/faculties")
    public String getFaculties(Model model, @RequestParam Map<String, String> params) {
        String kw = params.getOrDefault("kw", "");
        model.addAttribute("faculties", this.facultyService.getFaculties(kw));
        return "faculties";
    }
    
    @GetMapping("/faculties/add")
    public String createFacultyView(Model model) {
        Faculty faculty = new Faculty();
        model.addAttribute("faculty", faculty);
        return "add-faculty";
    }
    
    @PostMapping("/faculties")
    public String createFaculty(@ModelAttribute("faculty") @Valid Faculty faculty, 
                                   BindingResult result, 
                                   Model model) {
        if (result.hasErrors()) {
            return "add-faculty";
        }

        try {
            this.facultyService.addOrUpdate(faculty); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm khoa đào tạo.");
            return "add-faculty";
        }

        return "redirect:/admins/faculties";
    }
    
    @GetMapping("/faculties/{id}")
    public String updateFacultyView(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("faculty", this.facultyService.getFacultyById(id));
        return "update-faculty";
    }
    
    @PostMapping("/faculties/{id}")
    public String updateFaculty(@ModelAttribute("faculty") @Valid Faculty faculty, 
                                   BindingResult result, 
                                   Model model) {
        if (result.hasErrors()) {
            return "update-faculty"; 
        }

        try {
            System.out.println(faculty.getName());
            this.facultyService.addOrUpdate(faculty); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật khoa đào tạo.");
            return "update-faculty";
        }

        return "redirect:/admins/faculties";
    }
    
    @GetMapping("/majors")
    public String getMajors(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        model.addAttribute("majors", this.majorService.getMajors(params));
        
        return "majors";
    }
    
    @GetMapping("/majors/add")
    public String createMajorView(Model model) {
        Major major = new Major();
        model.addAttribute("major", major);
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        return "add-major";
    }

    @PostMapping("/majors")
    public String createMajor(@ModelAttribute("major") @Valid Major major, 
                              BindingResult result, 
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            return "add-major";
        }

        try {
            Integer facultyId = major.getFacultyId().getId(); // Lấy facultyId từ object major
            Faculty faculty = this.facultyService.getFacultyById(facultyId); 
            major.setFacultyId(faculty);
            this.majorService.addOrUpdate(major); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm ngành.");
            return "add-major";
        }

        return "redirect:/admins/majors";
    }
    
    @GetMapping("/majors/{id}")
    public String updateMajorView(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("major", this.majorService.getMajorById(id));
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        return "update-major";
    }
    
    @PostMapping("/majors/{id}")
    public String updateMajor(@ModelAttribute("major") @Valid Major major, 
                              BindingResult result, 
                              @RequestParam("facultyId") int facultyId,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            return "update-major";
        }

        try {
            Faculty faculty = this.facultyService.getFacultyById(facultyId);
            major.setFacultyId(faculty);
            this.majorService.addOrUpdate(major); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật ngành.");
            return "update-major";
        }

        return "redirect:/admins/majors";
    }
    
    @GetMapping("/classes")
    public String getClasses(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("classes", this.classService.getClasses(params));
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        model.addAttribute("majors", this.majorService.getMajors(null));
        return "classes";
    }

    @GetMapping("/classes/add")
    public String addClassView(Model model) {
        model.addAttribute("aClass", new Class());
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
//        model.addAttribute("majors", this.majorService.getMajors(null));
        return "add-class"; 
    }

    @PostMapping("/classes")
    public String addClass(@ModelAttribute("aClass") @Valid Class aClass, 
                              BindingResult result, 
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
//            model.addAttribute("majors", this.majorService.getMajors(null));
            
            System.err.println(result.getAllErrors());
            return "add-class"; 
        }

        try {
            this.classService.addOrUpdate(aClass); 
            return "redirect:/admins/classes"; 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm lớp.");
            return "add-class"; 
        }
    }

    @GetMapping("/classes/{id}")
    public String updateClassView(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("aClass", this.classService.getClassById(id));
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
//        model.addAttribute("majors", this.majorService.getMajors(null));
        return "update-class"; 
    }

    @PostMapping("/classes/{id}")
    public String updateClass(@ModelAttribute("aClass") @Valid Class aClass,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
//            model.addAttribute("majors", this.majorService.getMajors(null));
            return "update-class"; 
        }

        try {
            this.classService.addOrUpdate(aClass); 
            return "redirect:/admins/classes"; 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật lớp.");
            return "update-class"; 
        }
    }
    
    @GetMapping("/affairs")
    public String listAffair(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("affairs", this.affairSevice.getAffairs(params));
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        return "affairs";
    }
    
    @GetMapping("/affairs/add")
    public String createAffairView(Model model) {
        Affair a = new Affair();
        a.setAccountId(new Account());
        model.addAttribute("affair", a);
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        
        return "add-affair";
    }
    
    @PostMapping("/affairs/add")
    public String createAffair(@ModelAttribute("affair") @Valid Affair affair, 
                      BindingResult result,
                      HttpServletRequest request,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            
            return "add-affair";
        }

        try {
            this.affairSevice.addOrUpdate(affair);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            return "add-affair";
        }
        
        return "redirect:/admins/affairs";
    }
    
    @GetMapping("/affairs/{id}")
    public String updateAffairView(Model model, @PathVariable(value = "id") int id) {
        Affair a = this.affairSevice.getAffairById(id);
        a.getAccountId().setPassword(null);
        model.addAttribute("affair", a);
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        
        return "update-affair";
    }
    
    @PostMapping("/affairs/{id}")
    public String updateAffair(@ModelAttribute("affair") @Valid Affair affair, 
                          BindingResult result,
                          HttpServletRequest request,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            System.err.println(result.getAllErrors());
            return "update-affair";
        }

        try {
            this.affairSevice.addOrUpdate(affair);
        } catch (Exception e) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            System.err.println(e.getMessage());
            return "update-affair";
        }

        return "redirect:/admins/affairs";
    }
    
    @GetMapping("/lecturers")
    public String listLecturer(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("lecturers", this.lecturerService.getLecturers(params));
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        return "lecturers";
    }
    
    @GetMapping("/lecturers/add")
    public String createLecturerView(Model model) {
        Lecturer a = new Lecturer();
        a.setAccountId(new Account());
        model.addAttribute("lecturer", a);
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        
        return "add-lecturer";
    }
    
    @PostMapping("/lecturers/add")
    public String createLecturer(@ModelAttribute("lecturer") @Valid Lecturer lecturer, 
                      BindingResult result,
                      HttpServletRequest request,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            
            return "add-lecturer";
        }

        try {
            this.lecturerService.addOrUpdate(lecturer);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            return "add-lecturer";
        }
        
        return "redirect:/admins/lecturers";
    }
    
    @GetMapping("/lecturers/{id}")
    public String updateLecturerView(Model model, @PathVariable(value = "id") int id) {
        Lecturer a = this.lecturerService.getLecturerById(id);
        a.getAccountId().setPassword(null);
        model.addAttribute("lecturer", a);
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        
        return "update-lecturer";
    }
    
    @PostMapping("/lecturers/{id}")
    public String updateLecturer(@ModelAttribute("lecturer") @Valid Lecturer lecturer, 
                          BindingResult result,
                          HttpServletRequest request,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            System.err.println(result.getAllErrors());
            return "update-lecturer";
        }

        try {
            this.lecturerService.addOrUpdate(lecturer);
        } catch (Exception e) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            System.err.println(e.getMessage());
            return "update-lecturer";
        }

        return "redirect:/admins/lecturers";
    }
    
    @GetMapping("/students")
    public String listStudents(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("students", this.studentService.getStudents(params));
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        model.addAttribute("majors", this.majorService.getMajors(null));
        return "students";
    }
    
    @GetMapping("/students/add")
    public String createStudentView(Model model) {
        Student a = new Student();
        a.setAccountId(new Account());
        model.addAttribute("student", a);
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        
        return "add-student";
    }
    
    @PostMapping("/students/add")
    public String createStudent(@ModelAttribute("student") @Valid Student student, 
                      BindingResult result,
                      HttpServletRequest request,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            
            return "add-student";
        }

        try {
            this.studentService.addOrUpdate(student);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            return "add-student";
        }
        
        return "redirect:/admins/students";
    }
    
    @GetMapping("/students/{id}")
    public String updateStudentView(Model model, @PathVariable(value = "id") int id) {
        Student a = this.studentService.getStudentById(id);
        a.getAccountId().setPassword(null);
        model.addAttribute("student", a);
        model.addAttribute("faculties", this.facultyService.getFaculties(""));
        
        return "update-student";
    }
    
    @PostMapping("/students/{id}")
    public String updateStudent(@ModelAttribute("student") @Valid Student student, 
                          BindingResult result,
                          HttpServletRequest request,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            System.err.println(result.getAllErrors());
            return "update-student";
        }

        try {
            this.studentService.addOrUpdate(student);
        } catch (Exception e) {
            model.addAttribute("faculties", this.facultyService.getFaculties(""));
            System.err.println(e.getMessage());
            return "update-student";
        }

        return "redirect:/admins/students";
    }
    
    // --------------
    // return json
    // --------------
    @GetMapping("/major-by-facultyId")
    public @ResponseBody List<Major> getMajorsByFaculty(@RequestParam Map<String, String> params) {
        String type = params.get("type");
        String kw = params.get("kw");
        
        System.out.println("type: " + type + ", kw: " + kw);  // Debugging


        if ("faculty".equals(type) && kw != null && !kw.isEmpty()) {
            return this.majorService.getMajors(params);
        }

        return this.majorService.getMajors(null);
    }
    
    @GetMapping("/class-by-majorId")
    public @ResponseBody List<Class> getClassByMajor(@RequestParam Map<String, String> params) {
        String type = params.get("type");
        String kw = params.get("kw");
        
        System.out.println("type: " + type + ", kw: " + kw);  // Debugging


        if ("major".equals(type) && kw != null && !kw.isEmpty()) {
            return this.classService.getClasses(params);
        }

        return this.classService.getClasses(params);
    }
}
