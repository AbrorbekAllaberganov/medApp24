package com.example.medic.service;

import com.example.medic.entity.doctor.Doctor;
import com.example.medic.entity.superEntity.Parent;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.DoctorPayload;
import com.example.medic.payload.Result;
import com.example.medic.repository.DoctorRepository;
import com.example.medic.repository.ParentRepository;
import com.example.medic.repository.RoleRepository;
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
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final ParentRepository parentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyFileService myFileService;
    private final Logger logger= LoggerFactory.getLogger(DoctorService.class);
    Result result=new Result();

    public Result saveDoctor(DoctorPayload doctorPayload){
        try {
            Doctor doctor=new Doctor();

            Parent parent=new Parent();
            parent.setFullName(doctorPayload.getFullName());
            parent.setUserName(doctorPayload.getPassportNumber()+doctorPayload.getPassportSeries());
            parent.setPhoneNumber(doctorPayload.getPhoneNumber());
            parent.setRoles(roleRepository.findByName("ROLE_DOCTOR"));
            parent.setPassword(passwordEncoder.encode(getPassword()));
            parentRepository.save(parent);

            doctor.setParent(parent);
            doctor.setImage(myFileService.findByHashId(doctorPayload.getImageId()));
            doctor.setCertifkat(myFileService.findByHashId(doctorPayload.getCertificateId()));
            doctor.setPassportNumber(doctorPayload.getPassportNumber());
            doctor.setPassportSeries(doctorPayload.getPassportSeries());
            doctor.setAbout(doctorPayload.getAbout());

            doctorRepository.save(doctor);

            return result.save(doctor);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result.error();
    }

    public Result editDoctor(UUID doctorId,DoctorPayload doctorPayload){
        try {
            Doctor doctor=findDoctor(doctorId);

            Parent parent=doctor.getParent();
            parent.setFullName(doctorPayload.getFullName());
            parent.setUserName(doctorPayload.getUserName());
            parent.setPhoneNumber(doctorPayload.getPhoneNumber());
            parent.setPassword(passwordEncoder.encode(getPassword()));
            parentRepository.save(parent);

            doctor.setParent(parent);
            doctor.setImage(myFileService.findByHashId(doctorPayload.getImageId()));
            doctor.setCertifkat(myFileService.findByHashId(doctorPayload.getCertificateId()));
            doctor.setPassportNumber(doctorPayload.getPassportNumber());
            doctor.setPassportSeries(doctorPayload.getPassportSeries());

            doctorRepository.save(doctor);

            return result.save(doctor);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result.error();
    }

    public Result deleteDoctor(UUID doctorId){
        try {
            Doctor doctor=findDoctor(doctorId);

            Parent parent=doctor.getParent();
            doctorRepository.delete(doctor);
            parentRepository.delete(parent);

            return result.delete();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result.error();
    }

    public Page<Doctor> getAll(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        return doctorRepository.findAll(pageable);
    }

    public Doctor findDoctor(UUID doctorId){
        return doctorRepository.findById(doctorId).orElseThrow(()->new ResourceNotFound("doctor","id",doctorId));

    }

    public String getPassword(){
        Random random=new Random();
        int password= random.nextInt(900000)+100000;
        return String.valueOf(password);
    }
}
