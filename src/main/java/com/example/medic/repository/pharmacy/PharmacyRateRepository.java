package com.example.medic.repository.pharmacy;

import com.example.medic.entity.doctor.DoctorRate;
import com.example.medic.entity.pharmacy.PharmacyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PharmacyRateRepository extends JpaRepository<PharmacyRate, UUID> {
    @Query(value = "SELECT AVG(rate) FROM pharmacy_rate WHERE pharmacy_id=:idPharmacy",nativeQuery = true)
    Double getRatePharmacy (@Param("idPharmacy")UUID pharmacyId);


    List<PharmacyRate> findAllByPharmacy_Id(UUID pharmacyId);



}
