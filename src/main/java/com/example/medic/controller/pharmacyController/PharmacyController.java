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
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editPharmacy(@PathVariable UUID id,
                                               @RequestBody PharmacyPayload pharmacyPayload){
        Result result =pharmacyService.editPharmacy(id,pharmacyPayload);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deletePharmacy(@PathVariable UUID id){
        Result result =pharmacyService.deletePharmacy(id);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Result> getAllPharmacy(){
        Result result=pharmacyService.getAllPharmacy();
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/get-by-rate")
    public ResponseEntity<Result> getAllPharmacySortedByRate(){
        Result result=pharmacyService.getByRate();
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/get-rate/{id}")
    public ResponseEntity<Result> getPharmacyRate(@PathVariable UUID id){
        Result result=pharmacyService.getPharmacyRate(id);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Result> searchByName(@PathVariable String name){
        Result result= pharmacyService.searchByName(name);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/add-medicine")
    public ResponseEntity<Result> addMedicineInPharmacy(@RequestParam("medicineId")UUID medicineId,
                                                        @RequestParam("pharmacyId")UUID pharmacyId){
        Result result= pharmacyService.addMedicine(pharmacyId,medicineId);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }
    @GetMapping("/get-medicine/{id}")
    public ResponseEntity<Result> getMedicineByPharmacyId(@PathVariable UUID id){
        Result result=pharmacyService.getMedicine(id);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

}
