package com.example.medic.loader;

import com.example.medic.entity.Admin;
import com.example.medic.entity.superEntity.Parent;
import com.example.medic.entity.superEntity.Role;
import com.example.medic.repository.AdminRepository;
import com.example.medic.repository.ParentRepository;
import com.example.medic.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
        @Value("${spring.jpa.hibernate.ddl-auto}")
        private String init;

        private final RoleRepository roleRepository;
        private final AdminRepository adminRepository;
        private final PasswordEncoder passwordEncoder;
        private final ParentRepository parentRepository;
        private final Logger logger= LoggerFactory.getLogger(DataLoader.class);

    @Override
    public void run(String... args) throws Exception {


        try {
            if (init.equalsIgnoreCase("create"))
            {
                Role roleUser=new Role();
                roleUser.setId(1L);
                roleUser.setName("ROLE_USER");

                Role roleAdmin=new Role();
                roleAdmin.setId(2L);
                roleAdmin.setName("ROLE_ADMIN");

                Role roleDoctor=new Role();
                roleDoctor.setId(3L);
                roleDoctor.setName("ROLE_DOCTOR");

                Role roleSuperAdmin=new Role();
                roleSuperAdmin.setId(4L);
                roleSuperAdmin.setName("ROLE_SUPERADMIN");

                List<Role> roleList=new ArrayList<>(Arrays.asList(roleUser,roleAdmin,roleDoctor,roleSuperAdmin));
                roleRepository.saveAll(roleList);

                Admin admin=new Admin();
                Parent parent=new Parent();

                parent.setRoles(roleRepository.findByName("ROLE_SUPERADMIN"));
                parent.setUserName("admin");
                parent.setPassword(passwordEncoder.encode("111"));
                parent.setFullName("Abror Allaberganov");
                parent.setPhoneNumber("+998977777777");
                parentRepository.save(parent);

                admin.setParent(parent);

                adminRepository.save(admin);
            }


        }catch (Exception e)
        {
            logger.error(e.getMessage());
        }
    }
}
