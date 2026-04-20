package ra.dto;

import java.time.LocalDate;

public class CourseDTO {
    private Integer id;
    private String courseName;
    private Integer duration;
    private String instructor;
    private LocalDate createAt;

    public CourseDTO(Integer id, String courseName, Integer duration, String instructor, java.time.LocalDate createAt) {
        this.id = id;
        this.courseName = courseName;
        this.duration = duration;
        this.instructor = instructor;
        this.createAt = createAt;
    }

    public CourseDTO(Integer id, String name, Integer duration, String instructor) {
        this.id = id;
        this.courseName = name;
        this.duration = duration;
        this.instructor = instructor;
    }

    public CourseDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public java.time.LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(java.time.LocalDate createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", duration=" + duration +
                ", instructor='" + instructor + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
