package com.example.medic.controller.admin;

import com.example.medic.payload.AdminRequest;
import com.example.medic.payload.Result;
import com.example.medic.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.UUID;

@RestController
@RequestMapping("/api/super-admin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final SuperAdminService superAdminService;

    @PostMapping("/save-admin")
    public ResponseEntity<Result> saveAdmin(@RequestBody AdminRequest adminRequest){
        Result result=superAdminService.saveAdmin(adminRequest);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

//    @PutMapping("/edit-admin/{id}")
//    public ResponseEntity<Result> editAdmin(@PathVariable UUID id,@RequestBody AdminRequest adminRequest){
//        Result result= superAdminService.saveAdmin(adminRequest);
//    }
}
