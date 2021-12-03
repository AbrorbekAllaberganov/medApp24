package com.example.medic.service.pharmacy;

import com.example.medic.entity.pharmacy.Medicine;
import com.example.medic.entity.pharmacy.MedicineSum;
import com.example.medic.entity.pharmacy.Pharmacy;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.Result;
import com.example.medic.payload.pharmacy.MedicinePayload;
import com.example.medic.payload.pharmacy.MedicineSumPayload;
import com.example.medic.repository.pharmacy.MedicineSumRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MedicineSumService {
    private final MedicineSumRepository medicineSumRepository;
    private final MedicineService medicineService;
    private final PharmacyService pharmacyService;
    private final Logger logger= LoggerFactory.getLogger(MedicineSumService.class);
    Result result=new Result();

    public Result saveMedicineSum(MedicineSumPayload medicineSumPayload){
        try {
            MedicineSum medicineSum=new MedicineSum();
            medicineSum.setSum(medicineSumPayload.getSum());
            medicineSum.setMedicine(medicineService.findMedicine(medicineSumPayload.getMedicineId()));
            medicineSum.setPharmacy(pharmacyService.findPharmacyById(medicineSumPayload.getPharmacyId()));

            medicineSumRepository.save(medicineSum);

            return result.save(medicineSum);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result edit(UUID medicineSumId,MedicineSumPayload medicineSumPayload){
        try {
            MedicineSum medicineSum=findById(medicineSumId);
            medicineSum.setSum(medicineSumPayload.getSum());
            medicineSumRepository.save(medicineSum);

            return result.save(medicineSum);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result delete(UUID medicineSumId){
        try {
            medicineSumRepository.deleteById(medicineSumId);
            return result.delete();
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getAll(){
        return result.success(medicineSumRepository.findAll());
    }

    public MedicineSum findById(UUID medicineSumId){
        return medicineSumRepository.findById(medicineSumId).orElseThrow(()->new ResourceNotFound("medicine","id",medicineSumId));
    }

}
