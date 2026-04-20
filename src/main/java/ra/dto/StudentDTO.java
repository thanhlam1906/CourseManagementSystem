package ra.dto;

import java.time.LocalDate;

public class StudentDTO {
    private Integer id;
    private String name;
    private LocalDate dob;
    private String email;
    private Boolean sex;
    private String phone;
    private String password;
    private LocalDate createAt ;

    public StudentDTO() {
    }

    public StudentDTO(String name, LocalDate dob, String email, Boolean sex, String phone, LocalDate createAt) {
        this(name, dob, email, sex, phone, null, createAt);
    }

    public StudentDTO(String name, LocalDate dob, String email, Boolean sex, String phone, String password, LocalDate createAt) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.createAt = createAt;
    }

    public StudentDTO(Integer id, String name, LocalDate dob, String email, Boolean sex, String phone, LocalDate createAt) {
        this(id, name, dob, email, sex, phone, null, createAt);
    }

    public StudentDTO(Integer id, String name, LocalDate dob, String email, Boolean sex, String phone, String password, LocalDate createAt) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.createAt = createAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", sex=" + sexStr +
                ", phone='" + phone + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
