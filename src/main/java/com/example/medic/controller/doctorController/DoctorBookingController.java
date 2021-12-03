package com.example.medic.controller.doctorController;

import com.example.medic.payload.Result;
import com.example.medic.service.doctor.DoctorBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/doctor-booking")
@RequiredArgsConstructor
public class DoctorBookingController {
    private final DoctorBookingService doctorBookingService;

    @GetMapping("/get-all")
    public ResponseEntity<Result> getAllBooking() {
        Result result = doctorBookingService.getAll();
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteBooking(@PathVariable UUID id) {
        Result result = doctorBookingService.delete(id);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }
}
