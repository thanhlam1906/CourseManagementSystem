package ra.dao.impl;

import ra.dao.StudentDAO;
import ra.model.Student;
import ra.utils.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private static final String SELECT_ALL_SQL = "select * from student";
    private static final String INSERT_SQL = "insert into student(name, dob, email, sex, phone, password) values(?,?,?,?,?,?)";
    private static final String DELETE_SQL = "delete from student where id = ?";
    private static final String UPDATE_SQL = "update student set name = ?, dob = ?, email = ?, sex = ?, phone = ?, password = ? where id = ?";
    private static final String FIND_BY_ID_SQL = "select * from student where id = ?";
    private static final String SEARCH_BY_NAME_SQL = "select * from student where name ilike ?";

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                students.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public boolean addStudent(Student student) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            ps.setString(1, student.getName());
            ps.setDate(2, student.getDob() != null ? Date.valueOf(student.getDob()) : null);
            ps.setString(3, student.getEmail());
            setSexBit(ps, 4, student.isSex());
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteStudent(String id) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, Integer.parseInt(id));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, student.getName());
            ps.setDate(2, student.getDob() != null ? Date.valueOf(student.getDob()) : null);
            ps.setString(3, student.getEmail());
            setSexBit(ps, 4, student.isSex());
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
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Student> searchByName(String name) {
        List<Student> students = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SEARCH_BY_NAME_SQL)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    static String toBitValue(Boolean value) {
        return Boolean.TRUE.equals(value) ? "1" : "0";
    }

    private static void setSexBit(PreparedStatement ps, int parameterIndex, Boolean sex) throws SQLException {
        if (sex == null) {
            ps.setNull(parameterIndex, Types.BIT);
            return;
        }
        ps.setString(parameterIndex, toBitValue(sex));
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        Date dob = rs.getDate("dob");
        if (dob != null) {
            student.setDob(dob.toLocalDate());
        }
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        String sex = rs.getString("sex");
        if (sex != null) {
            student.setSex("1".equals(sex.trim()));
        }
        student.setPhone(rs.getString("phone"));
        student.setPassword(rs.getString("password"));
        Date createAt = rs.getDate("create_at");
        if (createAt != null) {
            student.setCreateAt(createAt.toLocalDate());
        }
        return student;
    }
}
