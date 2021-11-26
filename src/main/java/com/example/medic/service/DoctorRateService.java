package com.example.medic.service;

import com.example.medic.entity.doctor.DoctorRate;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.DoctorPayload;
import com.example.medic.payload.DoctorRatePayload;
import com.example.medic.payload.Result;
import com.example.medic.repository.DoctorRateRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorRateService {
    private final DoctorRateRepository doctorRateRepository;
    private final DoctorService doctorService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(DoctorRateService.class);
    Result result = new Result();

    public Result saveRate(DoctorRatePayload doctorRatePayload) {
        try {
            DoctorRate doctorRate = new DoctorRate();

            doctorRate.setDoctor(doctorService.findDoctor(doctorRatePayload.getDoctorId()));
            doctorRate.setUser(userService.findUser(doctorRatePayload.getUserId()));
            doctorRate.setComment(doctorRatePayload.getComment());
            doctorRate.setRate(doctorRatePayload.getRate());

            doctorRateRepository.save(doctorRate);

            return result.save(doctorRate);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result.error();
    }

    public Result editRate(UUID userId, UUID doctorId, DoctorRatePayload doctorRatePayload) {
        try {
            DoctorRate doctorRate = doctorRateRepository.fidRateByUserId_DoctorId(userId, doctorId);

            if (doctorRatePayload.getComment() != null)
                doctorRate.setComment(doctorRatePayload.getComment());
            if (doctorRatePayload.getRate() != null)
                doctorRate.setRate(doctorRatePayload.getRate());

            doctorRateRepository.save(doctorRate);

            return result.edit(doctorRate);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result.error();
    }

    public Result delete(UUID id){
        try {
            DoctorRate doctorRate=findDoctorRate(id);
            doctorRateRepository.delete(doctorRate);
            return result.delete();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result.error();
    }

    public List<DoctorRate> getRateDoctorId(UUID doctorId){
        try {
            return doctorRateRepository.findAllByDoctor_Id(doctorId);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public DoctorRate findDoctorRate(UUID doctorRateId){
        return doctorRateRepository.findById(doctorRateId).orElseThrow(()->new ResourceNotFound("id","doctor-rate",doctorRateId));
    }

    public Double getDoctorRate(UUID doctorId){
        return doctorRateRepository.getRateDoctor(doctorId);
    }

}
