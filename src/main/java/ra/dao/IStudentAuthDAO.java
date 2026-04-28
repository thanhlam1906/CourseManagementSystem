package ra.dao;

import ra.model.Student;

public interface IStudentAuthDAO {
    /**
     * Tim student theo email + password.
     * @return Student neu thong tin dung, null neu khong tim thay.
     */
    Student findByEmailAndPassword(String email, String password);
}

