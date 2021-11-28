package com.example.medic.service.pharmacy;

import com.example.medic.entity.pharmacy.Pharmacy;
import com.example.medic.entity.pharmacy.PharmacyRate;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.Result;
import com.example.medic.payload.pharmacy.PharmacyPayload;
import com.example.medic.payload.pharmacy.PharmacyRatePayload;
import com.example.medic.repository.pharmacy.PharmacyRateRepository;
import com.example.medic.repository.pharmacy.PharmacyRepository;
import com.example.medic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PharmacyRateService {
    private final PharmacyRateRepository pharmacyRateRepository;
    private final PharmacyRepository pharmacyRepository;
    private final UserService userService;
    private final PharmacyService pharmacyService;
    private final Logger logger = LoggerFactory.getLogger(PharmacyRateService.class);
    Result result = new Result();

    public Result PharmacyRateSave(PharmacyRatePayload pharmacyRatePayload) {
        try {
            PharmacyRate pharmacyRate = new PharmacyRate();

            Pharmacy pharmacy = pharmacyService.findPharmacyById(pharmacyRatePayload.getPharmacyId());

            pharmacyRate.setPharmacy(pharmacy);
            pharmacyRate.setUser(userService.findUser(pharmacyRatePayload.getUserId()));
            pharmacyRate.setRate(pharmacyRatePayload.getRate());
            pharmacyRate.setComment(pharmacyRatePayload.getComment());

            pharmacyRateRepository.save(pharmacyRate);

            pharmacy.setRate(pharmacyRateRepository.getRatePharmacy(pharmacyRatePayload.getPharmacyId()));
            pharmacyRepository.save(pharmacy);
            return result.save(pharmacyRate);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error();
        }
    }

    public Result editPharmacyRate(UUID pharmacyId, PharmacyRatePayload pharmacyRatePayload) {
        try {
            PharmacyRate pharmacyRate = findPharmacy(pharmacyId);
            Pharmacy pharmacy = pharmacyService.findPharmacyById(pharmacyRatePayload.getPharmacyId());

            pharmacyRate.setPharmacy(pharmacy);
            pharmacyRate.setUser(userService.findUser(pharmacyRatePayload.getUserId()));
            pharmacyRate.setRate(pharmacyRatePayload.getRate());
            pharmacyRate.setComment(pharmacyRatePayload.getComment());

            pharmacyRateRepository.save(pharmacyRate);

            pharmacy.setRate(pharmacyRateRepository.getRatePharmacy(pharmacyRatePayload.getPharmacyId()));
            pharmacyRepository.save(pharmacy);
            return result.edit(pharmacyRate);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error();
        }
    }

    public Result deletePharmacyRate(UUID pharmacyId) {
        try {
            PharmacyRate pharmacyRate = findPharmacy(pharmacyId);
            pharmacyRateRepository.delete(pharmacyRate);

            return result.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error();
        }
    }

    public List<PharmacyRate> getRateByPharmacyId(UUID pharmacyId){
        return pharmacyRateRepository.findAllByPharmacy_Id(pharmacyId);
    }

    public PharmacyRate findPharmacy(UUID pharmacyId) {
        return pharmacyRateRepository.findById(pharmacyId).orElseThrow(() -> new ResourceNotFound("id", "doctor-rate", pharmacyId));
    }

}
