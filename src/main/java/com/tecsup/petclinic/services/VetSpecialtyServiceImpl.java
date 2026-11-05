package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.SpecialtyMapper;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.mappers.VetSpecialtyMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import com.tecsup.petclinic.repositories.VetRepository;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository vetSpecialtyRepository;
    private final SpecialtyRepository specialtyRepository;
    private final VetRepository vetRepository;
    private final VetSpecialtyMapper vetSpecialtyMapper;
    private final SpecialtyMapper specialtyMapper;
    private final VetMapper vetMapper;

    @Override
    public VetSpecialtyDTO assign(VetSpecialtyDTO vetSpecialtyDTO) {
        VetSpecialty relation = vetSpecialtyRepository.save(vetSpecialtyMapper.mapToEntity(vetSpecialtyDTO));
        return vetSpecialtyMapper.mapToDto(relation);
    }

    @Override
    public void delete(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);
        VetSpecialty relation = vetSpecialtyRepository.findById(id)
                .orElseThrow(() -> new VetSpecialtyNotFoundException("Record not found...!"));
        vetSpecialtyRepository.delete(relation);
    }

    @Override
    public List<SpecialtyDTO> findSpecialtiesByVetId(Integer vetId) {
        List<Integer> specialtyIds = vetSpecialtyRepository.findByVetId(vetId).stream()
                .map(VetSpecialty::getSpecialtyId)
                .collect(Collectors.toList());
        return specialtyRepository.findAllById(specialtyIds).stream()
                .map(specialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetDTO> findVetsBySpecialtyId(Integer specialtyId) {
        List<Integer> vetIds = vetSpecialtyRepository.findBySpecialtyId(specialtyId).stream()
                .map(VetSpecialty::getVetId)
                .collect(Collectors.toList());
        return vetRepository.findAllById(vetIds).stream()
                .map(vetMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
