package com.example.medic.controller;

import com.example.medic.entity.superEntity.Parent;
import com.example.medic.payload.doctor.DoctorPayload;
import com.example.medic.payload.LoginPayload;
import com.example.medic.payload.Result;
import com.example.medic.payload.UserPayload;
import com.example.medic.repository.AdminRepository;
import com.example.medic.repository.ParentRepository;
import com.example.medic.security.JwtTokenProvider;

import com.example.medic.service.doctor.DoctorService;
import com.example.medic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ParentRepository parentRepository;
    private final UserService userService;
    private final DoctorService doctorService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPayload payload) {
        System.out.println(payload);

        Parent parent = parentRepository.findByUserName(payload.getUserName());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getUserName(), payload.getPassword()));
        String token = jwtTokenProvider.createToken(parent.getUserName(), parent.getRoles());

        if (token == null) {
            return new ResponseEntity("Xatolik", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map<String, Object> login = new HashMap<>();
        login.put("token", token);
        login.put("username", parent.getUserName());
        login.put("success", true);
        return ResponseEntity.ok(login);


    }

    @PostMapping("/user-register")
    public ResponseEntity<Result> userRegister(@RequestBody UserPayload userPayload) {
        Result result = userService.saveUser(userPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }

    @PostMapping("/doctor-register")
    public ResponseEntity<Result> doctorRegister(@RequestBody DoctorPayload doctorPayload) {
        Result result = doctorService.saveDoctor(doctorPayload);
        return ResponseEntity.status(result.isStatus() ? 200 : 409).body(result);
    }


}
