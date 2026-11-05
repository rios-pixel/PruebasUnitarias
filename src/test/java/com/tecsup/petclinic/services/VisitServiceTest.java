package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Test
    public void testFindVisitById() {
        VisitDTO visit = null;
        try {
            visit = visitService.findById(5);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(1, visit.getPetId());
        assertEquals(1, visit.getVetId());
        assertEquals(LocalDate.of(2024, 1, 15), visit.getVisitDate());
        assertEquals("general checkup", visit.getDescription());
        assertEquals(new BigDecimal("65.00"), visit.getCost());
    }

    @Test
    public void testFindVisitByDescription() {
        List<VisitDTO> visits = visitService.findByDescription("general checkup");

        assertEquals(1, visits.size());
        assertEquals(5, visits.get(0).getId());
        assertEquals("general checkup", visits.get(0).getDescription());
    }

    @Test
    public void testCreateVisit() {
        VisitDTO visitDTO = VisitDTO.builder()
                .petId(1)
                .vetId(1)
                .visitDate(LocalDate.of(2025, 1, 10))
                .description("new patient checkup")
                .cost(new BigDecimal("75.00"))
                .build();

        VisitDTO created = visitService.create(visitDTO);

        log.info("VISIT CREATED: {}", created);
        assertNotNull(created.getId());
        assertEquals(1, created.getPetId());
        assertEquals(1, created.getVetId());
        assertEquals(LocalDate.of(2025, 1, 10), created.getVisitDate());
        assertEquals("new patient checkup", created.getDescription());
        assertEquals(new BigDecimal("75.00"), created.getCost());
    }

    @Test
    public void testUpdateVisit() {
        VisitDTO created = visitService.create(VisitDTO.builder()
                .petId(1)
                .vetId(1)
                .visitDate(LocalDate.of(2025, 2, 1))
                .description("initial checkup")
                .cost(new BigDecimal("50.00"))
                .build());

        created.setPetId(2);
        created.setVetId(3);
        created.setVisitDate(LocalDate.of(2025, 3, 15));
        created.setDescription("updated examination");
        created.setCost(new BigDecimal("125.50"));

        VisitDTO updated = visitService.update(created);

        assertEquals(2, updated.getPetId());
        assertEquals(3, updated.getVetId());
        assertEquals(LocalDate.of(2025, 3, 15), updated.getVisitDate());
        assertEquals("updated examination", updated.getDescription());
        assertEquals(new BigDecimal("125.50"), updated.getCost());
    }

    @Test
    public void testDeleteVisit() {
        VisitDTO created = visitService.create(VisitDTO.builder()
                .petId(1)
                .vetId(1)
                .visitDate(LocalDate.of(2025, 4, 20))
                .description("visit to delete")
                .cost(new BigDecimal("40.00"))
                .build());

        try {
            visitService.delete(created.getId());
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VisitNotFoundException.class, () -> visitService.findById(created.getId()));
    }
}
