package ra.dao;

import ra.model.Student;
import ra.model.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> getAllStudents();
    boolean addStudent(Student student);
    boolean deleteStudent(String id);
    boolean updateStudent(Student course);
    Student findById(int id);
    List<Student> searchByName(String name);
}
