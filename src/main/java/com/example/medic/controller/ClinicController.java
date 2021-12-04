package com.example.medic.controller;

import com.example.medic.payload.Result;
import com.example.medic.payload.doctor.ClinicPayload;
import com.example.medic.service.doctor.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/clinic")
@RequiredArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;

    @PostMapping("/save")
    public ResponseEntity<Result> saveClinic(@RequestBody ClinicPayload clinicPayload) {
        Result result = clinicService.saveClinic(clinicPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @PutMapping("/edit/{clinicId}")
    public ResponseEntity<Result> editClinic(@PathVariable UUID clinicId,
                                             @RequestBody ClinicPayload clinicPayload) {
        Result result = clinicService.editClinic(clinicId, clinicPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @DeleteMapping("/delete/{clinicId}")
    public ResponseEntity<Result> deleteClinic(@PathVariable UUID clinicId) {
        Result result = clinicService.delete(clinicId);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Result> getAllClinic() {
        Result result = clinicService.getAll();
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @PostMapping("/add-doctor")
    public ResponseEntity<Result> addDoctor(@RequestParam("clinicId") UUID clinicId,
                                            @RequestParam("doctorId") UUID doctorId) {
        Result result = clinicService.addDoctor(clinicId, doctorId);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @GetMapping("/get-doctors/{clinicId}")
    public ResponseEntity<Result> getDoctors(@PathVariable UUID clinicId){
        Result result= clinicService.getDoctors(clinicId);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @DeleteMapping("/delete-doctor")
    public ResponseEntity<Result> deleteDoctor(@RequestParam("clinicId") UUID clinicId,
                                               @RequestParam("doctorId") UUID doctorId){
        Result result=clinicService.deleteDoctor(clinicId, doctorId);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }
}