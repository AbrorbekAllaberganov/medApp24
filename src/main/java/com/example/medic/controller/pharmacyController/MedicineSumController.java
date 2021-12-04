package com.example.medic.controller.pharmacyController;

import com.example.medic.payload.Result;
import com.example.medic.payload.pharmacy.MedicineSumPayload;
import com.example.medic.payload.pharmacy.PharmacyPayload;
import com.example.medic.service.pharmacy.MedicineSumService;
import com.example.medic.service.pharmacy.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/pharmacy")
@RequiredArgsConstructor
public class MedicineSumController {
    public final MedicineSumService medicineSumService;
    private final PharmacyService pharmacyService;

    @PostMapping("/add-medicine")
    public ResponseEntity<Result> addMedicineToPharmacy(@RequestBody MedicineSumPayload medicineSumPayload) {
        Result result = medicineSumService.saveMedicineSum(medicineSumPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @PutMapping("/edit-medicine/{medicineSumId}")
    public ResponseEntity<Result> editMedicine(@PathVariable UUID medicineSumId,
                                               @RequestBody MedicineSumPayload medicineSumPayload) {
        Result result = medicineSumService.edit(medicineSumId, medicineSumPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @DeleteMapping("/delete/{medicineSumId}")
    public ResponseEntity<Result> deleteMedicine(@PathVariable UUID medicineSumId) {
        Result result = medicineSumService.delete(medicineSumId);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Result> getAll() {
        Result result = medicineSumService.getAll();
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @PutMapping("/edit-info/{pharmacyId}")
    public ResponseEntity<Result> editPharmacy(@PathVariable UUID pharmacyId,
                                               @RequestBody PharmacyPayload pharmacyPayload) {
        Result result = pharmacyService.editPharmacy(pharmacyId, pharmacyPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

}
