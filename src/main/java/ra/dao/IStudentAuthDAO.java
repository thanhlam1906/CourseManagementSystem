package ra.dao;

import ra.model.Student;

public interface IStudentAuthDAO {

    Student findByEmailAndPassword(String email, String password);
}

