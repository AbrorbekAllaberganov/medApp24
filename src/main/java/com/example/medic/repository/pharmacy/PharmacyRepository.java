package com.example.medic.repository.pharmacy;

import com.example.medic.entity.doctor.Doctor;
import com.example.medic.entity.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, UUID> {
    @Query(value = "SELECT * FROM pharmacy ORDER BY rate DESC;",nativeQuery = true)
    List<Pharmacy> getPharmacyByRate();

    Pharmacy findByName (String name);
}
