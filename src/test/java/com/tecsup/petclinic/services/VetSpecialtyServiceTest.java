package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Test
    public void testAssignSpecialtyToVet() throws Exception {
        VetSpecialtyDTO assignment = VetSpecialtyDTO.builder()
                .vetId(1)
                .specialtyId(3)
                .certificationDate(LocalDate.of(2024, 5, 10))
                .yearsExperience(4)
                .primary(true)
                .notes("New dentistry certification")
                .build();

        VetSpecialtyDTO created = vetSpecialtyService.assign(assignment);

        log.info("VET SPECIALTY ASSIGNED: {}", created);
        assertEquals(1, created.getVetId());
        assertEquals(3, created.getSpecialtyId());
        assertEquals(LocalDate.of(2024, 5, 10), created.getCertificationDate());
        assertEquals(4, created.getYearsExperience());
        assertTrue(created.isPrimary());
        assertEquals("New dentistry certification", created.getNotes());

        // Keep this test's relation from affecting the fixture-based search tests.
        vetSpecialtyService.delete(1, 3);
    }

    @Test
    public void testFindSpecialtiesByVet() {
        Set<String> names = vetSpecialtyService.findSpecialtiesByVetId(3).stream()
                .map(SpecialtyDTO::getName)
                .collect(Collectors.toSet());

        assertEquals(Set.of("surgery", "dentistry"), names);
    }

    @Test
    public void testFindVetsBySpecialty() {
        Set<String> names = vetSpecialtyService.findVetsBySpecialtyId(2).stream()
                .map(VetDTO::getFirstName)
                .collect(Collectors.toSet());

        assertEquals(Set.of("Linda", "Rafael"), names);
    }

    @Test
    public void testDeleteVetSpecialtyRelation() throws Exception {
        vetSpecialtyService.assign(VetSpecialtyDTO.builder()
                .vetId(1)
                .specialtyId(2)
                .certificationDate(LocalDate.of(2025, 1, 15))
                .yearsExperience(1)
                .primary(false)
                .notes("Temporary relation for delete test")
                .build());

        vetSpecialtyService.delete(1, 2);

        assertTrue(vetSpecialtyService.findSpecialtiesByVetId(1).isEmpty());
    }
}
