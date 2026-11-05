package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class TypeServiceTest {

    @Autowired
    private TypeService typeService;

    @Test
    public void testFindTypeById() {
        TypeDTO type = null;
        try {
            type = typeService.findById(1);
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals("cat", type.getName());
        assertEquals("Domestic feline", type.getDescription());
        assertTrue(type.isActive());
        assertEquals("small", type.getSizeCategory());
        assertEquals(15, type.getAverageLifespan());
        assertEquals("medium", type.getCareLevel());
    }

    @Test
    public void testFindTypeByName() {
        List<TypeDTO> types = typeService.findByName("dog");

        assertEquals(1, types.size());
        assertEquals("dog", types.get(0).getName());
        assertEquals("Domestic canine", types.get(0).getDescription());
    }

    @Test
    public void testCreateType() {
        TypeDTO typeDTO = TypeDTO.builder()
                .name("ferret")
                .description("Domestic ferret")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(8)
                .careLevel("medium")
                .build();

        TypeDTO created = typeService.create(typeDTO);

        log.info("TYPE CREATED: {}", created);
        assertNotNull(created.getId());
        assertEquals("ferret", created.getName());
        assertEquals("Domestic ferret", created.getDescription());
        assertTrue(created.isActive());
        assertEquals("small", created.getSizeCategory());
        assertEquals(8, created.getAverageLifespan());
        assertEquals("medium", created.getCareLevel());
    }

    @Test
    public void testUpdateType() {
        TypeDTO created = typeService.create(TypeDTO.builder()
                .name("test type old")
                .description("Old description")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(5)
                .careLevel("low")
                .build());

        created.setName("test type updated");
        created.setDescription("Updated description");
        created.setActive(false);
        created.setSizeCategory("large");
        created.setAverageLifespan(12);
        created.setCareLevel("high");

        TypeDTO updated = typeService.update(created);

        assertEquals("test type updated", updated.getName());
        assertEquals("Updated description", updated.getDescription());
        assertFalse(updated.isActive());
        assertEquals("large", updated.getSizeCategory());
        assertEquals(12, updated.getAverageLifespan());
        assertEquals("high", updated.getCareLevel());
    }

    @Test
    public void testDeleteType() {
        TypeDTO created = typeService.create(TypeDTO.builder()
                .name("temporary type")
                .description("Type used by delete test")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(4)
                .careLevel("low")
                .build());

        try {
            typeService.delete(created.getId());
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(TypeNotFoundException.class, () -> typeService.findById(created.getId()));
    }
}
