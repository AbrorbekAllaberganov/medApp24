package com.example.medic.controller.adminContreller;

import com.example.medic.payload.AdminRequest;
import com.example.medic.payload.Result;
import com.example.medic.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/super-admin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final SuperAdminService superAdminService;

    @PostMapping("/save-admin")
    public ResponseEntity<Result> saveAdmin(@RequestBody AdminRequest adminRequest){
        Result result=superAdminService.saveAdmin(adminRequest);
        return ResponseEntity.status(result.isStatus()?200:409).body(result);
    }

}
