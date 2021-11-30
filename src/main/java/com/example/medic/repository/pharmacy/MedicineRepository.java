package com.example.medic.repository.pharmacy;

import com.example.medic.entity.pharmacy.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MedicineRepository  extends JpaRepository<Medicine, UUID> {
    @Query(value = "select * from medicine m inner join medicine_pharmacy  pm on m.id=pm.medicine_id  where pm.pharmacy_id=:idPharmacy",nativeQuery = true)
    List<Medicine> getMedicineByPharmacyId(@Param("idPharmacy")UUID pharmacyId);
}
