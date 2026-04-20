package ra.business.impl;

import ra.model.Student;
import ra.model.dto.StudentDTO;

public class StudentServiceImpl {
    Student toEntity(StudentDTO studentDTO) {
        Student entity = new Student();
        if (studentDTO.getId() != null) {
            entity.setId(studentDTO.getId());
        }
        entity.setName(studentDTO.getName());
        entity.setDob(studentDTO.getDob());
        entity.setEmail(studentDTO.getEmail());
        entity.setSex(studentDTO.getSex());
        entity.setPhone(studentDTO.getPhone());
        entity.setCreateAt(studentDTO.getCreateAt());
        return entity;
    }

    StudentDTO toDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setDob(student.getDob());
        dto.setEmail(student.getEmail());
        dto.setSex(student.isSex());
        dto.setPhone(student.getPhone());
        dto.setCreateAt(student.getCreateAt());
        return dto;
    }
}
