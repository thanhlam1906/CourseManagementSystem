package ra.model;

import java.time.LocalDateTime;

public class Enrollment {
    private Integer id;
    private LocalDateTime registeredAt;
    private EnrollmentStatus status;
    private Integer studentId;
    private Integer courseId;
    private Course course;
    public Enrollment() {}

    public Enrollment(Integer id, LocalDateTime registeredAt, EnrollmentStatus status, Integer studentId, Integer courseId, Course course) {
        this.id = id;
        this.registeredAt = registeredAt;
        this.status = status;
        this.studentId = studentId;
        this.courseId = courseId;
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", registeredAt=" + registeredAt +
                ", status=" + status +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
