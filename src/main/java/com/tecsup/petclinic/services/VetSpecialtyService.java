package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.util.List;

public interface VetSpecialtyService {

    VetSpecialtyDTO assign(VetSpecialtyDTO vetSpecialtyDTO);

    void delete(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    List<SpecialtyDTO> findSpecialtiesByVetId(Integer vetId);

    List<VetDTO> findVetsBySpecialtyId(Integer specialtyId);
}
