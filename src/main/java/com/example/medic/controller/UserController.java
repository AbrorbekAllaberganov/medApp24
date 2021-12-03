package com.example.medic.controller;

import com.example.medic.payload.Result;
import com.example.medic.payload.UserPayload;
import com.example.medic.payload.doctor.DoctorBookingPayload;
import com.example.medic.service.UserService;
import com.example.medic.service.doctor.DoctorBookingService;
import com.example.medic.service.doctor.DoctorService;
import com.example.medic.service.pharmacy.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final DoctorService doctorService;
    private final DoctorBookingService doctorBookingService;
    private final PharmacyService pharmacyService;

    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editUser(@PathVariable UUID id, @RequestBody UserPayload userPayload){
        Result result=userService.editUser(id,userPayload);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteUser(@PathVariable UUID id){
        Result result= userService.deleteUser(id);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @GetMapping("/get-doctors")
    public ResponseEntity<Result> getAllDoctors(){
        Result result=doctorService.getAll();
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @GetMapping("/get-rate-doctors")
    public ResponseEntity<Result> getAllDoctorsByRate(){
        Result result=doctorService.getAllByRate();
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @PostMapping("/booking-doctor")
    public ResponseEntity<Result> bookingDoctor(@RequestBody DoctorBookingPayload doctorBookingPayload){
        Result result= doctorBookingService.save(doctorBookingPayload);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Result> searchByName(@PathVariable String name){
        Result result= pharmacyService.searchByName(name);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

}
