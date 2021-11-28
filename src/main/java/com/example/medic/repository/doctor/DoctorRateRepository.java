package com.example.medic.repository.doctor;

import com.example.medic.entity.doctor.DoctorRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorRateRepository extends JpaRepository<DoctorRate, UUID> {

    @Query(value = "select * from doctor_rate where user_id=:idUser and doctor_id=:idDoctor",nativeQuery = true)
    DoctorRate fidRateByUserId_DoctorId(@Param("idUser")UUID userId,@Param("idDoctor")UUID doctorId);

    List<DoctorRate> findAllByDoctor_Id(UUID doctorId);

    @Query(value = "SELECT AVG(rate) FROM doctor_rate WHERE doctor_id=:idDoctor",nativeQuery = true)
    Double getRateDoctor (@Param("idDoctor")UUID doctorId);
}
