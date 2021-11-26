package com.example.medic.service;

import com.example.medic.entity.Admin;
import com.example.medic.entity.superEntity.Parent;
import com.example.medic.payload.AdminRequest;
import com.example.medic.payload.Result;
import com.example.medic.repository.AdminRepository;
import com.example.medic.repository.ParentRepository;
import com.example.medic.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperAdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final ParentRepository parentRepository;
    private final RoleRepository roleRepository;
    private final Logger logger= LoggerFactory.getLogger(SuperAdminService.class);
    Result result=new Result();

    public Result saveAdmin(AdminRequest adminRequest){
        try {
            Admin admin=new Admin();
            Parent parent=new Parent();

            parent.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
            parent.setFullName(adminRequest.getFullName());
            parent.setPhoneNumber(adminRequest.getPhoneNumber());
            if (parentRepository.findByUserName(adminRequest.getUserName())==null)
                parent.setUserName(adminRequest.getFullName());
            else
                return new Result("username isn't empty",false);

            parent.setRoles(roleRepository.findByName("ROLE_ADMIN"));
            parentRepository.save(parent);

            adminRepository.save(admin);
            return result.save(admin);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return result.error();
    }


}
