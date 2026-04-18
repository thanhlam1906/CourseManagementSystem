package ra.model.dto;

import java.time.LocalDate;

public class CourseDTO {
//    private Integer courseId;
    private String courseName;
    private Integer duration;
    private String instructor;
    private LocalDate createAt;

    public CourseDTO( String courseName, Integer duration, String instructor, java.time.LocalDate createAt) {
//        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.instructor = instructor;
        this.createAt = createAt;
    }

    public CourseDTO(Integer id, String name, Integer duration, String instructor) {
    }

    public CourseDTO() {

    }

//
//    public Integer getCourseId() {
//        return courseId;
//    }
//
//    public void setCourseId(Integer courseId) {
//        this.courseId = courseId;
//    }

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
//                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", duration=" + duration +
                ", instructor='" + instructor + '\'' +
                '}';
    }
}
