package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class SpecialityServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    @Test
    public void testFindSpecialtyById() {
        SpecialtyDTO specialty = null;
        try {
            specialty = specialtyService.findById(1);
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals("radiology", specialty.getName());
        assertEquals("Farewell", specialty.getOffice());
        assertEquals(8, specialty.getHOpen());
        assertEquals(18, specialty.getHClose());
    }

    @Test
    public void testFindSpecialtyByName() {
        List<SpecialtyDTO> specialties = specialtyService.findByName("surgery");

        assertEquals(1, specialties.size());
        assertEquals("surgery", specialties.get(0).getName());
        assertEquals("Maryland", specialties.get(0).getOffice());
    }

    @Test
    public void testCreateSpecialty() {
        SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
                .name("oncology")
                .office("New Clinic")
                .hOpen(7)
                .hClose(17)
                .build();

        SpecialtyDTO created = specialtyService.create(specialtyDTO);

        log.info("SPECIALTY CREATED: {}", created);
        assertNotNull(created.getId());
        assertEquals("oncology", created.getName());
        assertEquals("New Clinic", created.getOffice());
        assertEquals(7, created.getHOpen());
        assertEquals(17, created.getHClose());
    }

    @Test
    public void testUpdateSpecialty() {
        SpecialtyDTO created = specialtyService.create(SpecialtyDTO.builder()
                .name("dermatology")
                .office("Old Clinic")
                .hOpen(8)
                .hClose(16)
                .build());

        created.setName("neurology");
        created.setOffice("New Clinic");
        created.setHOpen(9);
        created.setHClose(19);

        SpecialtyDTO updated = specialtyService.update(created);

        assertEquals("neurology", updated.getName());
        assertEquals("New Clinic", updated.getOffice());
        assertEquals(9, updated.getHOpen());
        assertEquals(19, updated.getHClose());
    }

    @Test
    public void testDeleteSpecialty() {
        SpecialtyDTO created = specialtyService.create(SpecialtyDTO.builder()
                .name("test specialty")
                .office("Temporary Office")
                .hOpen(8)
                .hClose(17)
                .build());

        try {
            specialtyService.delete(created.getId());
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(SpecialtyNotFoundException.class,
                () -> specialtyService.findById(created.getId()));
    }
}
