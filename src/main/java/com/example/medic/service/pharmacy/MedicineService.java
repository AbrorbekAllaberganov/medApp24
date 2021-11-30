package com.example.medic.service.pharmacy;

import com.example.medic.entity.pharmacy.Medicine;
import com.example.medic.entity.pharmacy.Pharmacy;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.Result;
import com.example.medic.payload.pharmacy.MedicinePayload;
import com.example.medic.repository.pharmacy.MedicineRepository;
import com.example.medic.repository.pharmacy.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final PharmacyRepository pharmacyRepository;
    private final Logger logger = LoggerFactory.getLogger(MedicineService.class);
    Result result = new Result();

    public Result saveMedicine(MedicinePayload medicinePayload) {
        try {
            Medicine medicine = new Medicine();
            medicine.setName(medicinePayload.getName());
            medicine.setPharmacy(pharmacyRepository.findAllById(medicinePayload.getPharmacyId()));

            medicineRepository.save(medicine);

            return result.save(medicine);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result editMedicine(UUID medicineId, MedicinePayload medicinePayload) {
        try {
            Medicine medicine = findMedicine(medicineId);
            medicine.setName(medicinePayload.getName());
            medicineRepository.save(medicine);

            return result.save(medicine);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result deleteMedicine(UUID medicineId){
        try {
            Medicine medicine=findMedicine(medicineId);
            medicineRepository.delete(medicine);
            return result.delete();
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getAllMedicine(){
        return result.success(medicineRepository.findAll());
    }

    public Medicine findMedicine(UUID medicineId) {
        return medicineRepository.findById(medicineId).orElseThrow(()->new ResourceNotFound("medicine","id",medicineId));
    }

    public Result getMedicineByPharmacyId(UUID pharmacyId){
        try {
            return result.success(medicineRepository.getMedicineByPharmacyId(pharmacyId));
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }
}
