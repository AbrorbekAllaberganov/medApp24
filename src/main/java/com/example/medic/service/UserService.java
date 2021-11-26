package com.example.medic.service;

import com.example.medic.entity.User;
import com.example.medic.entity.superEntity.Parent;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.Result;
import com.example.medic.payload.UserPayload;
import com.example.medic.repository.ParentRepository;
import com.example.medic.repository.RoleRepository;
import com.example.medic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ParentRepository parentRepository;
    private final Logger logger= LoggerFactory.getLogger(UserService.class);
    Result result=new Result();

    public Result saveUser(UserPayload userPayload){
        try {
            User user =new User();

            Parent parent=new Parent();
            parent.setFullName(userPayload.getFullName());
            parent.setUserName(userPayload.getUserName());
            parent.setPhoneNumber(userPayload.getPhoneNumber());
            parent.setRoles(roleRepository.findByName("ROLE_USER"));
            parent.setPassword(passwordEncoder.encode(userPayload.getPassword()));

            parentRepository.save(parent);
            user.setParent(parent);
            userRepository.save(user);
            return result.save(user);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result.error();
    }

    public Result editUser(UUID userId,UserPayload userPayload){
        try {
            User user =findUser(userId);

            Parent parent=user.getParent();
            parent.setFullName(userPayload.getFullName());
            parent.setUserName(userPayload.getUserName());
            parent.setPhoneNumber(userPayload.getPhoneNumber());
            parent.setPassword(passwordEncoder.encode(userPayload.getPassword()));

            parentRepository.save(parent);
            user.setParent(parent);
            userRepository.save(user);
            return result.edit(user);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result.error();
    }

    public Result deleteUser(UUID userId){
        try {
            User user =findUser(userId);
//            Parent parent=user.getParent()
//            parentRepository.delete(parent);
            userRepository.delete(user);
            return result.delete();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result.error();
    }

    public Page<User> getAll(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public User findUser(UUID userId){
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFound("user","id",userId));
    }
}
