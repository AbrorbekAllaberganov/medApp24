package com.example.medic.controller;

import com.example.medic.payload.Result;
import com.example.medic.payload.UserPayload;
import com.example.medic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editUser(@PathVariable UUID id, @RequestBody UserPayload userPayload){
        Result result=userService.editUser(id,userPayload);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteUser(@PathVariable UUID id){
        Result result= userService.deleteUser(id);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

}
