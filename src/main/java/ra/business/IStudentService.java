package ra.business;

import ra.dto.StudentDTO;

import java.util.List;

public interface IStudentService {
    List<StudentDTO> getAllStudents();
    boolean addStudent(StudentDTO studentDTO);
    boolean deleteStudent(String id);
    boolean updateStudent(StudentDTO studentDTO);
    StudentDTO getStudentById(int id);
    List<StudentDTO> sortNameStudents(boolean isAscending);
}
