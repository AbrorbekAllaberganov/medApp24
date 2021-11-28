package com.example.medic.repository.doctor;

import com.example.medic.entity.doctor.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkingTimeRepository extends JpaRepository<WorkingTime, UUID> {
}
