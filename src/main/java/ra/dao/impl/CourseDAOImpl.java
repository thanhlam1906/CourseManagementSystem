package ra.dao.impl;

import ra.dao.CourseDAO;
import ra.model.Course;
import ra.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {
    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();

        String sql = "select * from course";
        try(Connection con  = DBUtil.getConnection(); PreparedStatement ps = con.prepareCall(sql); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreateAt(rs.getDate("create_at").toLocalDate());
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public boolean addCourse(Course course) {
        String sql = "insert into course(name, duration, instructor) values(?,?,?)";
        try ( Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareCall(sql)){
            ps.setString(1, course.getName());
            ps.setInt(2, course.getDuration());
            ps.setString(3, course.getInstructor());
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCourse(String id) {
        String sql = "delete from course where id = ?";
        try(Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareCall(sql)){
            ps.setInt(1, Integer.parseInt(id));
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCourse(Course course) {
        String sql = "update course set name = ?, duration = ?, instructor = ? where id = ?";
        try(Connection con = DBUtil.getConnection();PreparedStatement ps = con.prepareCall(sql)){
            ps.setString(1, course.getName());
            ps.setInt(2, course.getDuration());
            ps.setString(3, course.getInstructor());
            ps.setInt(4, course.getId());
            ps.execute();
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course findById(int id) {
        String sql = "select * from course where id = ?";
        try(Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareCall(sql)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    Course course = new Course();
                    course.setId(rs.getInt("id"));
                    course.setName(rs.getString("name"));
                    course.setDuration(rs.getInt("duration"));
                    course.setInstructor(rs.getString("instructor"));
                    course.setCreateAt(rs.getDate("create_at").toLocalDate());
                    return course;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Course> searchByName(String name) {
        String sql = "select id, name, instructor, create_at from course where name ilike ?";
        List<Course> courses = new ArrayList<>();
        try(Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareCall(sql);   ){
            ps.setString(1, "%" + name + "%");
            try(ResultSet resultSet = ps.executeQuery()){
                while (resultSet.next()){
                    Course course = new Course();
                    ps.setString(1, "%" + name + "%");
                    course.setId(resultSet.getInt("id"));
                    course.setName(resultSet.getString("name"));
                    course.setInstructor(resultSet.getString("instructor"));
                    course.setDuration(resultSet.getInt("duration"));
                    course.setCreateAt(resultSet.getDate("create_at").toLocalDate());
                    courses.add(course);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }
}
