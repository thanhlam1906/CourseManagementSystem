package ra.business;

import ra.dto.StudentLoginDTO;
import ra.model.Student;

public interface IStudentAuthService {
    /**
     * Dang nhap voi vai tro hoc vien.
     * @return Student neu dang nhap thanh cong, null neu sai thong tin.
     * @throws IllegalArgumentException neu DTO khong hop le.
     */
    Student login(StudentLoginDTO dto);
}

