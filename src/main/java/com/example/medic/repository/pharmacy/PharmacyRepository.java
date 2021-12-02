package com.example.medic.repository.pharmacy;

import com.example.medic.entity.doctor.Doctor;
import com.example.medic.entity.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, UUID> {
    @Query(value = "SELECT * FROM pharmacy ORDER BY rate DESC;",nativeQuery = true)
    List<Pharmacy> getPharmacyByRate();

    @Query(value ="select * from pharmacy p inner join medicine_pharmacy mp on p.id=mp.pharmacy_id where mp.medicine_id=:medicineId" ,nativeQuery = true)
    List<Pharmacy> getAllPharmacyByMedicineId(@Param("medicineId")UUID medicineId);

    Pharmacy findByName (String name);
}
