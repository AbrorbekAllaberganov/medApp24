package com.example.medic.service.pharmacy;

import com.example.medic.entity.doctor.WorkingTime;
import com.example.medic.entity.pharmacy.Medicine;
import com.example.medic.entity.pharmacy.Pharmacy;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.doctor.WorkingTimePayload;
import com.example.medic.payload.pharmacy.PharmacyPayload;
import com.example.medic.payload.Result;
import com.example.medic.repository.pharmacy.MedicineRepository;
import com.example.medic.repository.pharmacy.PharmacyRateRepository;
import com.example.medic.repository.pharmacy.PharmacyRepository;
import com.example.medic.service.MyFileService;
import com.example.medic.service.doctor.WorkingTimeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyService {
    private final PharmacyRepository pharmacyRepository;
    private final MyFileService myFileService;
    private final PharmacyRateRepository pharmacyRateRepository;
    private final WorkingTimeService workingTimeService;
    private final MedicineRepository medicineRepository;
    private final MedicineService medicineService;
    private final Logger logger = LoggerFactory.getLogger(PharmacyService.class);
    Result result = new Result();

    public Result savePharmacy(PharmacyPayload pharmacyPayload) {
        try {
            Pharmacy pharmacy = new Pharmacy();

            pharmacy.setName(pharmacyPayload.getName());
            pharmacy.setAbout(pharmacyPayload.getAbout());
            pharmacy.setAddress(pharmacyPayload.getAddress());
            pharmacy.setLan(pharmacyPayload.getLan());
            pharmacy.setLat(pharmacyPayload.getLat());
            pharmacy.setImage(myFileService.findByHashId(pharmacyPayload.getImageHashId()));

            List<WorkingTimePayload> workingTimePayloads = pharmacyPayload.getWorkingTimePayloadList();
            List<WorkingTime> workingTimeList = workingTimePayloads.stream()
                    .map(workingTimeService::save).collect(Collectors.toList());

//            pharmacy.setMedicines(medicineRepository.findAllById(pharmacyPayload.getMedicineIds()));

            pharmacy.setPharmacyWorkings(workingTimeList);

            pharmacyRepository.save(pharmacy);

            return result.save(pharmacy);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result editPharmacy(UUID pharmacyId, PharmacyPayload pharmacyPayload) {
        try {
            Pharmacy pharmacy = findPharmacyById(pharmacyId);

            pharmacy.setName(pharmacyPayload.getName());
            pharmacy.setAbout(pharmacyPayload.getAbout());
            pharmacy.setAddress(pharmacyPayload.getAddress());
            pharmacy.setLan(pharmacyPayload.getLan());
            pharmacy.setLat(pharmacyPayload.getLat());
            pharmacy.setImage(myFileService.findByHashId(pharmacyPayload.getImageHashId()));

            pharmacyRepository.save(pharmacy);
            return result.save(pharmacy);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result deletePharmacy(UUID pharmacyId) {
        try {
            Pharmacy pharmacy = findPharmacyById(pharmacyId);
            pharmacyRepository.delete(pharmacy);
            return result.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getAllPharmacy() {
        return result.success(pharmacyRepository.findAll());
    }

    public Result getPharmacyRate(UUID pharmacyId) {
        try {
            return result.success(pharmacyRateRepository.getRatePharmacy(pharmacyId));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getByRate() {
        return result.success(pharmacyRepository.getPharmacyByRate());
    }

    public Pharmacy findPharmacyById(UUID pharmacyId) {
        return pharmacyRepository.findById(pharmacyId).orElseThrow(() -> new ResourceNotFound("pharmacy", "id", pharmacyId));
    }

    public Result searchByName(String name) {
        try {
            Pharmacy pharmacy = pharmacyRepository.findByName(name);
            return result.success(pharmacy);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result addMedicine(UUID pharmacyId,UUID medicineId){
        try{
            Pharmacy pharmacy=findPharmacyById(pharmacyId);
            Medicine medicine=medicineService.findMedicine(medicineId);
            Set<Pharmacy>pharmacySet=new HashSet<>(medicine.getPharmacy());
            pharmacySet.add(pharmacy);
            medicine.setPharmacy(new ArrayList<>(pharmacySet));

            medicineRepository.save(medicine);
            return result.success(medicine);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getMedicine(UUID pharmacyId){
        try {
            List<Medicine>medicineList= medicineRepository.getMedicineByPharmacyId(pharmacyId);
            return result.success(medicineList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getPharmacyByMedicineId(UUID medicineId){
        try {
            List<Pharmacy>pharmacyList= pharmacyRepository.getAllPharmacyByMedicineId(medicineId);
            return result.success(pharmacyList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

}
