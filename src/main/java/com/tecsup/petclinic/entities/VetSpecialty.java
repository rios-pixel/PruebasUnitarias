package com.tecsup.petclinic.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "vet_specialties")
@IdClass(VetSpecialtyId.class)
@NoArgsConstructor
@Data
@AllArgsConstructor
public class VetSpecialty {

    @Id
    private Integer vetId;
    @Id
    private Integer specialtyId;
    private LocalDate certificationDate;
    private Integer yearsExperience;
    @Column(name = "is_primary")
    private boolean primary;
    private String notes;
}
