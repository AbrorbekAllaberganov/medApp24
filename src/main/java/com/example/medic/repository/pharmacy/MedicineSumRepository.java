package com.example.medic.repository.pharmacy;

import com.example.medic.entity.pharmacy.MedicineSum;
import com.example.medic.entity.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MedicineSumRepository extends JpaRepository<MedicineSum, UUID> {
    List<MedicineSum> findAllByPharmacy_Id(UUID pharmacyId);

    @Query(value = "select ms.pharmacy_id from medicine_sum ms inner join medicine m on ms.medicine_id=m.id where m.name=:medicineName",nativeQuery = true)
    List<UUID>getAllPharmacyByMedicineName(@Param("medicineName") String medicineName);

    @Query(value = "select ms.medicine_id from medicine_sum ms  where ms.pharmacy_id=:pharmacyId",nativeQuery = true)
    List<UUID>getAllMedicineIdByPharmacy(@Param("pharmacyId") UUID pharmacyId);
}
