package com.example.medic.service.pharmacy;

import com.example.medic.entity.doctor.WorkingTime;
import com.example.medic.entity.pharmacy.Pharmacy;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.doctor.WorkingTimePayload;
import com.example.medic.payload.pharmacy.PharmacyPayload;
import com.example.medic.payload.Result;
import com.example.medic.repository.pharmacy.PharmacyRateRepository;
import com.example.medic.repository.pharmacy.PharmacyRepository;
import com.example.medic.service.MyFileService;
import com.example.medic.service.doctor.WorkingTimeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyService {
    private final PharmacyRepository pharmacyRepository;
    private final MyFileService myFileService;
    private final PharmacyRateRepository pharmacyRateRepository;
    private final WorkingTimeService workingTimeService;
    private final Logger logger= LoggerFactory.getLogger(PharmacyService.class);
    Result result=new Result();

    public Result savePharmacy(PharmacyPayload pharmacyPayload){
        try{
            Pharmacy pharmacy=new Pharmacy();

            pharmacy.setName(pharmacyPayload.getName());
            pharmacy.setAbout(pharmacyPayload.getAbout());
            pharmacy.setAddress(pharmacyPayload.getAddress());
            pharmacy.setLan(pharmacyPayload.getLan());
            pharmacy.setLat(pharmacyPayload.getLat());
            pharmacy.setImage(myFileService.findByHashId(pharmacyPayload.getImageHashId()));

            List<WorkingTimePayload>workingTimePayloads=pharmacyPayload.getWorkingTimePayloadList();
            List<WorkingTime>workingTimeList=workingTimePayloads.stream()
                    .map(workingTimeService::save).collect(Collectors.toList());

            pharmacy.setPharmacyWorkings(workingTimeList);

            pharmacyRepository.save(pharmacy);

            return result.save(pharmacy);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return result.error();
    }

    public Result editPharmacy(UUID pharmacyId,PharmacyPayload pharmacyPayload){
        try{
            Pharmacy pharmacy=findPharmacyById(pharmacyId);

            pharmacy.setName(pharmacyPayload.getName());
            pharmacy.setAbout(pharmacyPayload.getAbout());
            pharmacy.setAddress(pharmacyPayload.getAddress());
            pharmacy.setLan(pharmacyPayload.getLan());
            pharmacy.setLat(pharmacyPayload.getLat());
            pharmacy.setImage(myFileService.findByHashId(pharmacyPayload.getImageHashId()));

            pharmacyRepository.save(pharmacy);
            return result.save(pharmacy);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return result.error();
    }

    public Result deletePharmacy(UUID pharmacyId){
        try {
            Pharmacy pharmacy=findPharmacyById(pharmacyId);
            pharmacyRepository.delete(pharmacy);
            return result.delete();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result.error();
    }

    public List<Pharmacy> getAllPharmacy(){
        return pharmacyRepository.findAll();
    }

    public Double getPharmacyRate(UUID pharmacyId){
        return pharmacyRateRepository.getRatePharmacy(pharmacyId);
    }

    public List<Pharmacy> getByRate(){
        return pharmacyRepository.getPharmacyByRate();
    }

    public Pharmacy findPharmacyById(UUID pharmacyId){
        return pharmacyRepository.findById(pharmacyId).orElseThrow(()->new ResourceNotFound("pharmacy","id",pharmacyId));
    }

    public Result searchByName(String name){
        try {
            Pharmacy pharmacy=pharmacyRepository.findByName(name);
            return result.success(pharmacy);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return result.error();
    }


}
