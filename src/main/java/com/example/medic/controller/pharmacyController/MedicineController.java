package com.example.medic.controller.pharmacyController;

import com.example.medic.payload.Result;
import com.example.medic.payload.pharmacy.MedicinePayload;
import com.example.medic.service.pharmacy.MedicineService;
import com.example.medic.service.pharmacy.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/medicine")
public class MedicineController {
    private final MedicineService medicineService;
    private final PharmacyService pharmacyService;

    @PostMapping("/save")
    public ResponseEntity<Result> saveMedicine(@RequestBody MedicinePayload medicinePayload){
        Result result= medicineService.saveMedicine(medicinePayload);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editMedicine(@PathVariable UUID id,@RequestBody MedicinePayload medicinePayload){
        Result result= medicineService.editMedicine(id,medicinePayload);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteMedicine(@PathVariable UUID id){
        Result result= medicineService.deleteMedicine(id);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Result> getAllMedicine(){
        return ResponseEntity.ok(medicineService.getAllMedicine());
    }

    @GetMapping("/get-by-pharmacy/{pharmacyId}")
    public ResponseEntity<Result> getMedicineByPharmacyId(@PathVariable UUID pharmacyId){
        Result result=medicineService.getMedicineByPharmacyId(pharmacyId);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/search-pharmacy/{medicineId}")
    public ResponseEntity<Result> getPharmacyByMedicineId(@PathVariable UUID medicineId){
        Result result= pharmacyService.getPharmacyByMedicineId(medicineId);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }
}
