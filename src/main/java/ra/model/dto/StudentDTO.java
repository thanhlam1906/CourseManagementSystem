package ra.model.dto;

import java.time.LocalDate;

public class StudentDTO {
    private String name;
    private LocalDate dob;
    private String email;
    private Boolean sex;
    private String phone;
    private LocalDate createAt ;

    public StudentDTO() {
    }

    public StudentDTO(String name, LocalDate dob, String email, Boolean sex, String phone, LocalDate createAt) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        String sexStr = "Không xác định";
        if (sex != null) {
            sexStr = sex ? "Nam" : "Nữ";
        }
        return "StudentDTO{" +
                "name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", sex=" + sexStr +
                ", phone='" + phone + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
