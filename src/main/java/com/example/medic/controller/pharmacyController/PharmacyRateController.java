package com.example.medic.controller.pharmacyController;

import com.example.medic.payload.Result;
import com.example.medic.payload.pharmacy.PharmacyRatePayload;
import com.example.medic.service.pharmacy.PharmacyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/rate-pharmacy")
@RequiredArgsConstructor
public class PharmacyRateController {
    private final PharmacyRateService pharmacyRateService;

    @PostMapping("/save")
    public ResponseEntity<Result> savePharmacyRate(@RequestBody PharmacyRatePayload pharmacyRatePayload){
        Result result= pharmacyRateService.PharmacyRateSave(pharmacyRatePayload);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editPharmacyRate(@PathVariable UUID id,
                                                   @RequestBody PharmacyRatePayload pharmacyRatePayload){
        Result result= pharmacyRateService.editPharmacyRate(id, pharmacyRatePayload);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deletePharmacyRate(@PathVariable UUID id){
        Result result= pharmacyRateService.deletePharmacyRate(id);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/get/{pharmacyId}")
    public ResponseEntity<Result> getAllByPharmacyId(@PathVariable UUID pharmacyId){
        Result result=pharmacyRateService.getRateByPharmacyId(pharmacyId);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }



}
