package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testFindOwnerById() {
        OwnerDTO owner = null;
        try {
            owner = ownerService.findById(1);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals("George", owner.getFirstName());
        assertEquals("Franklin", owner.getLastName());
    }

    @Test
    public void testFindOwnerByName() {
        List<OwnerDTO> owners = ownerService.findByName("Davis");

        assertEquals(2, owners.size());
        assertTrue(owners.stream().allMatch(owner -> "Davis".equals(owner.getLastName())));
    }

    @Test
    public void testCreateOwner() {
        OwnerDTO ownerDTO = OwnerDTO.builder()
                .firstName("Alex")
                .lastName("Morgan")
                .address("100 New Street")
                .city("Madison")
                .telephone("6085559999")
                .build();

        OwnerDTO created = ownerService.create(ownerDTO);

        log.info("OWNER CREATED: {}", created);
        assertNotNull(created.getId());
        assertEquals("Alex", created.getFirstName());
        assertEquals("Morgan", created.getLastName());
        assertEquals("100 New Street", created.getAddress());
        assertEquals("Madison", created.getCity());
        assertEquals("6085559999", created.getTelephone());
    }

    @Test
    public void testUpdateOwner() {
        OwnerDTO created = ownerService.create(OwnerDTO.builder()
                .firstName("Taylor")
                .lastName("Reed")
                .address("10 Old Street")
                .city("Old City")
                .telephone("6085551111")
                .build());

        created.setFirstName("Taylor2");
        created.setLastName("Reed2");
        created.setAddress("20 New Street");
        created.setCity("New City");
        created.setTelephone("6085552222");

        OwnerDTO updated = ownerService.update(created);

        assertEquals("Taylor2", updated.getFirstName());
        assertEquals("Reed2", updated.getLastName());
        assertEquals("20 New Street", updated.getAddress());
        assertEquals("New City", updated.getCity());
        assertEquals("6085552222", updated.getTelephone());
    }

    @Test
    public void testDeleteOwner() {
        OwnerDTO created = ownerService.create(OwnerDTO.builder()
                .firstName("Jordan")
                .lastName("Lee")
                .address("30 Test Avenue")
                .city("Madison")
                .telephone("6085553333")
                .build());

        try {
            ownerService.delete(created.getId());
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(OwnerNotFoundException.class, () -> ownerService.findById(created.getId()));
    }
}
