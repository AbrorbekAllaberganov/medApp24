package com.example.medic.controller.doctorController;

import com.example.medic.payload.doctor.DoctorPayload;
import com.example.medic.payload.Result;
import com.example.medic.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editDoctor(@PathVariable UUID id, @RequestBody DoctorPayload doctorPayload){
        Result result= doctorService.editDoctor(id,doctorPayload);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteDoctor(@PathVariable UUID id){
        Result result= doctorService.deleteDoctor(id);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }



}
