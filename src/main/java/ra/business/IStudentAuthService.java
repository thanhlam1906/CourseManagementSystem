package ra.business;

import ra.dto.StudentLoginDTO;
import ra.model.Student;

public interface IStudentAuthService {

    Student login(StudentLoginDTO dto);
}

