package com.example.medic.service.doctor;

import com.example.medic.entity.doctor.Doctor;
import com.example.medic.entity.doctor.WorkingTime;
import com.example.medic.entity.superEntity.Parent;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.doctor.DoctorPayload;
import com.example.medic.payload.Result;
import com.example.medic.payload.doctor.WorkingTimePayload;
import com.example.medic.repository.doctor.DoctorRepository;
import com.example.medic.repository.ParentRepository;
import com.example.medic.repository.RoleRepository;
import com.example.medic.service.MyFileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final ParentRepository parentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyFileService myFileService;
    private final WorkingTimeService workingTimeService;
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
            doctor.setCertificate(myFileService.findByHashId(doctorPayload.getCertificateId()));
            doctor.setPassportNumber(doctorPayload.getPassportNumber());
            doctor.setPassportSeries(doctorPayload.getPassportSeries());
            doctor.setAbout(doctorPayload.getAbout());
            doctor.setDuringTime(doctorPayload.getDuringTime());

            List<WorkingTimePayload> workingTimePayloads = doctorPayload.getWorkingTimePayloadList();

            List<WorkingTime> workingTimes = workingTimePayloads.stream()
                    .map(workingTimeService::save).collect(Collectors.toList());

            doctor.setWorkingTime(workingTimes);
            doctorRepository.save(doctor);

            return result.save(doctor);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
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
            doctor.setCertificate(myFileService.findByHashId(doctorPayload.getCertificateId()));
            doctor.setPassportNumber(doctorPayload.getPassportNumber());
            doctor.setPassportSeries(doctorPayload.getPassportSeries());
            doctor.setDuringTime(doctorPayload.getDuringTime());

            doctorRepository.save(doctor);

            return result.save(doctor);
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
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
            return result.error(e);
        }
    }

    public Result getAll(){
        return result.success(doctorRepository.findAll());
    }

    public Result getAllByRate(){
        List<Doctor>doctorList=doctorRepository.getDoctorsByRate();
        return result.success(doctorList);
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
