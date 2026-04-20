package ra.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseDTOTest {
    @Test
    void constructorShouldSetId() {
        CourseDTO dto = new CourseDTO(5, "Java", 40, "Teacher");
        assertEquals(5, dto.getId());
    }
}
