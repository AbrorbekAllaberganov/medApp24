package com.example.medic.controller.pharmacy;

import com.example.medic.payload.Result;
import com.example.medic.payload.pharmacy.MedicinePayload;
import com.example.medic.service.pharmacy.MedicineService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/medicine")
public class MedicineController {
    private final MedicineService medicineService;

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

    @GetMapping("/get-by-pharmacy/{id}")
    public ResponseEntity<Result> getMedicineByPharmacyId(@PathVariable UUID id){
        Result result=medicineService.getMedicineByPharmacyId(id);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }
}
