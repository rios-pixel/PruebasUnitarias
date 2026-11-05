package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    public void testFindVetById() {
        VetDTO vet = null;
        try {
            vet = vetService.findById(1);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }
        assertEquals("James", vet.getFirstName());
        assertEquals("Carter", vet.getLastName());
    }

    @Test
    public void testFindVetByName() {
        List<VetDTO> vets = vetService.findByName("James");
        assertEquals(1, vets.size());
        assertEquals("James", vets.get(0).getFirstName());
    }

    @Test
    public void testCreateVet() {
        VetDTO vetDTO = VetDTO.builder()
                .firstName("Alex")
                .lastName("Morgan")
                .email("alex.morgan@petclinic.com")
                .phone("6085559999")
                .active(true)
                .build();

        VetDTO created = vetService.create(vetDTO);

        log.info("VET CREATED: {}", created);
        assertNotNull(created.getId());
        assertEquals("Alex", created.getFirstName());
        assertEquals("Morgan", created.getLastName());
        assertEquals("alex.morgan@petclinic.com", created.getEmail());
        assertEquals("6085559999", created.getPhone());
        assertTrue(created.isActive());
    }

    @Test
    public void testUpdateVet() {
        VetDTO created = vetService.create(VetDTO.builder()
                .firstName("Taylor")
                .lastName("Reed")
                .email("taylor.reed@petclinic.com")
                .phone("6085551111")
                .active(true)
                .build());

        created.setFirstName("Taylor2");
        created.setLastName("Reed2");
        created.setEmail("taylor.reed2@petclinic.com");
        created.setPhone("6085552222");
        created.setActive(false);

        VetDTO updated = vetService.update(created);

        assertEquals("Taylor2", updated.getFirstName());
        assertEquals("Reed2", updated.getLastName());
        assertEquals("taylor.reed2@petclinic.com", updated.getEmail());
        assertEquals("6085552222", updated.getPhone());
        assertFalse(updated.isActive());
    }

    @Test
    public void testDeleteVet() {
        VetDTO created = vetService.create(VetDTO.builder()
                .firstName("Jordan")
                .lastName("Lee")
                .email("jordan.lee@petclinic.com")
                .phone("6085553333")
                .active(true)
                .build());

        try {
            vetService.delete(created.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VetNotFoundException.class, () -> vetService.findById(created.getId()));
    }
}
