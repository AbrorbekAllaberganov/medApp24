package com.example.medic.controller.admin;

import com.example.medic.payload.doctor.DoctorPayload;
import com.example.medic.payload.Result;
import com.example.medic.service.doctor.DoctorService;
import com.example.medic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final DoctorService doctorService;

    @GetMapping("/get-users")
    public ResponseEntity<?> getUsers(@RequestParam("page")int page,
                                      @RequestParam("size")int size){
        return ResponseEntity.ok(userService.getAll(page, size));
    }

    @PostMapping("/doctor-save")
    public ResponseEntity<Result> saveDoctor(@RequestBody DoctorPayload doctorPayload){
        Result result= doctorService.saveDoctor(doctorPayload);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }


}
