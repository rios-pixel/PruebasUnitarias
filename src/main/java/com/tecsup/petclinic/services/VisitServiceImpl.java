package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mappers.VisitMapper;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    @Override
    public VisitDTO create(VisitDTO visitDTO) {
        Visit newVisit = visitRepository.save(visitMapper.mapToEntity(visitDTO));
        return visitMapper.mapToDto(newVisit);
    }

    @Override
    public VisitDTO update(VisitDTO visitDTO) {
        Visit updatedVisit = visitRepository.save(visitMapper.mapToEntity(visitDTO));
        return visitMapper.mapToDto(updatedVisit);
    }

    @Override
    public void delete(Integer id) throws VisitNotFoundException {
        VisitDTO visit = findById(id);
        visitRepository.delete(visitMapper.mapToEntity(visit));
    }

    @Override
    public VisitDTO findById(Integer id) throws VisitNotFoundException {
        Optional<Visit> visit = visitRepository.findById(id);
        if (!visit.isPresent()) {
            throw new VisitNotFoundException("Record not found...!");
        }
        return visitMapper.mapToDto(visit.get());
    }

    @Override
    public List<VisitDTO> findByDescription(String description) {
        List<Visit> visits = visitRepository.findByDescription(description);
        visits.forEach(visit -> log.info("{}", visit));
        return visits.stream().map(visitMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<VisitDTO> findByPetId(Integer petId) {
        List<Visit> visits = visitRepository.findByPetId(petId);
        visits.forEach(visit -> log.info("{}", visit));
        return visits.stream().map(visitMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<VisitDTO> findAll() {
        return visitRepository.findAll().stream().map(visitMapper::mapToDto).collect(Collectors.toList());
    }
}
