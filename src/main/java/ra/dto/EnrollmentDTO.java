package ra.dto;

import ra.model.Course;
import ra.model.Student;

import java.time.LocalDateTime;

public class EnrollmentDTO {
    private Integer enrollment_id;
    private Integer student_id;
    private Integer course_id;
    private String courseName;
    private String status;
    private LocalDateTime createAt;
    private Course course;
    private Student student;

    public Course getCourse() {
        return course;
    }

    public Integer getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(Integer enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCourseName() {
        return courseName;
    }


    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public EnrollmentDTO() {
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    @Override
    public String toString() {
        return "EnrollmentDTO{" +
                "student_id=" + student_id +
                ", course_id=" + course_id +
                '}';
    }
}
