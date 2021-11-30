package com.example.medic.controller.doctorController;

import com.example.medic.payload.doctor.DoctorRatePayload;
import com.example.medic.payload.Result;
import com.example.medic.service.doctor.DoctorRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/rate-doctor")
@RequiredArgsConstructor
public class DoctorRateController {
    private final DoctorRateService doctorRateService;

    @PostMapping("/save")
    public ResponseEntity<Result> saveRate(@RequestBody DoctorRatePayload doctorRatePayload) {
        Result result = doctorRateService.saveRate(doctorRatePayload);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    @PutMapping("/edit")
    public ResponseEntity<Result> editRate(@RequestParam("userId") UUID userId,
                                       @RequestParam("doctorId") UUID teacherId,
                                       @RequestBody DoctorRatePayload doctorRatePayload) {
        Result result = doctorRateService.editRate(userId, teacherId, doctorRatePayload);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteRate(@PathVariable UUID id){
        Result result= doctorRateService.delete(id);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/get/{doctorId}")
    public ResponseEntity<Result> getDoctorsByRate(@PathVariable UUID doctorId){
        Result result=doctorRateService.getRateDoctorId(doctorId);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/range/{id}")
    public ResponseEntity<Result> getDoctorRate(@PathVariable UUID id){
        Result result=doctorRateService.getDoctorRate(id);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }
}
