package com.example.medic.controller.pharmacy;

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
    public ResponseEntity<?> getAllPharmacy(){
        return ResponseEntity.ok(pharmacyService.getAllPharmacy());
    }

    @GetMapping("/get-by-rate")
    public ResponseEntity<?> getAllPharmacyRate(){
        return ResponseEntity.ok(pharmacyService.getByRate());
    }

    @GetMapping("/get-rate/{id}")
    public ResponseEntity<Double> getPharmacyRate(@PathVariable UUID id){
        return ResponseEntity.ok(pharmacyService.getPharmacyRate(id));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Result> searchByName(@PathVariable String name){
        Result result= pharmacyService.searchByName(name);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }


}
