package com.example.medic.service.doctor;

import com.example.medic.entity.doctor.Doctor;
import com.example.medic.entity.doctor.DoctorRate;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.doctor.DoctorRatePayload;
import com.example.medic.payload.Result;
import com.example.medic.repository.doctor.DoctorRateRepository;
import com.example.medic.repository.doctor.DoctorRepository;
import com.example.medic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorRateService {
    private final DoctorRateRepository doctorRateRepository;
    private final DoctorService doctorService;
    private final DoctorRepository doctorRepository;
    private final UserService userService;
    private final WorkingTimeService workingTimeService;
    private final Logger logger = LoggerFactory.getLogger(DoctorRateService.class);
    Result result = new Result();

    public Result saveRate(DoctorRatePayload doctorRatePayload) {
        try {
            DoctorRate doctorRate = new DoctorRate();
            Doctor doctor = doctorService.findDoctor(doctorRatePayload.getDoctorId());

            doctorRate.setDoctor(doctor);
            doctorRate.setUser(userService.findUser(doctorRatePayload.getUserId()));
            doctorRate.setComment(doctorRatePayload.getComment());
            doctorRate.setRate(doctorRatePayload.getRate());

            doctorRateRepository.save(doctorRate);

            doctor.setRate(doctorRateRepository.getRateDoctor(doctor.getId()));
            doctorRepository.save(doctor);

            return result.save(doctorRate);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }

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
            return result.error(e);
        }

    }

    public Result delete(UUID id) {
        try {
            DoctorRate doctorRate = findDoctorRate(id);
            doctorRateRepository.delete(doctorRate);
            return result.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getRateDoctorId(UUID doctorId) {
        try {
            return result.success(doctorRateRepository.findAllByDoctor_Id(doctorId));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public DoctorRate findDoctorRate(UUID doctorRateId) {
        return doctorRateRepository.findById(doctorRateId).orElseThrow(() -> new ResourceNotFound("id", "doctor-rate", doctorRateId));
    }

    public Result getDoctorRate(UUID doctorId) {
        try {
            return result.success(doctorRateRepository.getRateDoctor(doctorId));
        }catch (Exception e){
            logger.error(e.getMessage());
            return result.error(e);
        }
    }
}
