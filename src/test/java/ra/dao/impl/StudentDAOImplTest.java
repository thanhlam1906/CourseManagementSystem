package ra.dao.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentDAOImplTest {
    @Test
    void toBitValueShouldMapBooleanToBitString() {
        assertEquals("1", StudentDAOImpl.toBitValue(true));
        assertEquals("0", StudentDAOImpl.toBitValue(false));
        assertEquals("0", StudentDAOImpl.toBitValue(null));
    }
}
