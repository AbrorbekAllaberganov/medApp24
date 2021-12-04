package com.example.medic.repository.doctor;

import com.example.medic.entity.doctor.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClinicRepository  extends JpaRepository<Clinic, UUID> {
}
