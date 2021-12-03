package com.example.medic.controller.pharmacyController;

import com.example.medic.payload.pharmacy.PharmacyPayload;
import com.example.medic.payload.Result;
import com.example.medic.service.pharmacy.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {
    private final PharmacyService pharmacyService;

    @PostMapping("/save")
    public ResponseEntity<Result> savePharmacy(@RequestBody PharmacyPayload pharmacyPayload){
        Result result =pharmacyService.savePharmacy(pharmacyPayload);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deletePharmacy(@PathVariable UUID id){
        Result result =pharmacyService.deletePharmacy(id);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Result> getAllPharmacy(){
        Result result=pharmacyService.getAllPharmacy();
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @GetMapping("/get-by-rate")
    public ResponseEntity<Result> getAllPharmacySortedByRate(){
        Result result=pharmacyService.getByRate();
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @GetMapping("/get-rate/{id}")
    public ResponseEntity<Result> getPharmacyRate(@PathVariable UUID id){
        Result result=pharmacyService.getPharmacyRate(id);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }


    @GetMapping("/get-medicine/{id}")
    public ResponseEntity<Result> getMedicineByPharmacyId(@PathVariable UUID id){
        Result result=pharmacyService.getMedicine(id);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

}
