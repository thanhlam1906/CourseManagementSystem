package ra.dao.impl;

import ra.dao.StudentDAO;
import ra.model.Student;
import ra.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "Select id, name, dob, email, sex, phone, password, create_at from student";
        try(Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareCall(sql); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                Date dob = rs.getDate("dob");
                if (dob != null) {
                    student.setDob(dob.toLocalDate());
                }
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhone(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                Date createAt = rs.getDate("create_at");
                if (createAt != null) {
                    student.setCreateAt(createAt.toLocalDate());
                }
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public boolean addStudent(Student student) {
        String sql = "insert into student (name,dob,email,sex,phone,password,create_at) values(?,?,?,CAST(? AS bit),?,?,?)";
        try(Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareCall(sql)){
            ps.setString(1, student.getName());
            ps.setDate(2, Date.valueOf(student.getDob()));
            ps.setString(3, student.getEmail());
            ps.setString(4, student.isSex() ? "1" : "0");
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getPassword());
            ps.setDate(7, Date.valueOf(student.getCreateAt()));
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public boolean deleteStudent(int id) {
        String sql = "delete from student where id = ?";
        try(Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateStudent(Student student) {
        String sql = "update student set name = ?, dob = ?, email = ?, sex = CAST(? AS bit), phone = ?, password = ? where id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setDate(2, Date.valueOf(student.getDob()));
            ps.setString(3, student.getEmail());
            ps.setString(4, student.isSex() ? "1" : "0");
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getPassword());
            ps.setInt(7, student.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        @Override
    public Student findById(int id) {
        String sql = "select * from student where id = ?";
        try(Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareCall(sql)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    Date dob = rs.getDate("dob");
                    if (dob != null) {
                        student.setDob(dob.toLocalDate());
                    }
                    student.setEmail(rs.getString("email"));
                    student.setSex(rs.getBoolean("sex"));
                    student.setPhone(rs.getString("phone"));
                    student.setPassword(rs.getString("password"));
                    Date createAt = rs.getDate("create_at");
                    if (createAt != null) {
                        student.setCreateAt(createAt.toLocalDate());
                    }
                    return student;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean changePassword(Student student) {
        String sql = "update student set password = ? where id = ? and(email = ? or phone = ?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setString(1, student.getPassword());
            ps.setInt(2, student.getId());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhone());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
        @Override
    public List<Student> searchByName(String name) {
        String sql = "select * from student where name like ?";
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    Date dob = rs.getDate("dob");
                    if (dob != null) {
                        student.setDob(dob.toLocalDate());
                    }
                    student.setEmail(rs.getString("email"));
                    student.setSex(rs.getBoolean("sex"));
                    student.setPhone(rs.getString("phone"));
                    student.setPassword(rs.getString("password"));
                    Date createAt = rs.getDate("create_at");
                    if (createAt != null) {
                        student.setCreateAt(createAt.toLocalDate());
                    }
                    students.add(student);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
        }
    }
