package ra.dao;

import ra.model.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> getAllStudents();
    boolean addStudent(Student student);
    boolean deleteStudent(int id);
    boolean updateStudent(Student student);
    Student findById(int id);
    List<Student> searchByName(String name);

}
