package ra.business.impl;

import org.junit.jupiter.api.Test;
import ra.model.Student;
import ra.model.dto.StudentDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentServiceImplTest {
    @Test
    void toEntityShouldKeepDtoId() {
        StudentServiceImpl service = new StudentServiceImpl();
        StudentDTO dto = new StudentDTO();
        dto.setId(7);
        dto.setName("A");
        dto.setDob(LocalDate.of(2000, 1, 1));
        dto.setEmail("a@example.com");
        dto.setSex(true);
        dto.setPhone("0123");
        dto.setCreateAt(LocalDate.of(2024, 1, 1));

        Student entity = service.toEntity(dto);

        assertEquals(7, entity.getId());
    }
}
