package com.example.medic.repository.doctor;

import com.example.medic.entity.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    @Query(value = "SELECT * FROM doctor ORDER BY rate DESC;",nativeQuery = true)
    List<Doctor> getDoctorsByRate();
}
