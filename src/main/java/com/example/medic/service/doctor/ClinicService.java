package com.example.medic.service.doctor;

import com.example.medic.entity.doctor.Clinic;
import com.example.medic.entity.doctor.Doctor;
import com.example.medic.exceptions.ResourceNotFound;
import com.example.medic.payload.Result;
import com.example.medic.payload.doctor.ClinicPayload;
import com.example.medic.repository.doctor.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final DoctorService doctorService;
    private final Logger logger = LoggerFactory.getLogger(ClinicService.class);
    Result result = new Result();

    public Result saveClinic(ClinicPayload clinicPayload) {
        try {
            Clinic clinic = new Clinic();
            clinic.setAddress(clinicPayload.getAddress());
            clinic.setLat(clinicPayload.getLat());
            clinic.setLon(clinicPayload.getLan());
            clinic.setName(clinicPayload.getName());
            clinicRepository.save(clinic);

            return result.save(clinic);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result editClinic(UUID clinicId, ClinicPayload clinicPayload) {
        try {
            Clinic clinic = findClinic(clinicId);
            clinic.setAddress(clinicPayload.getAddress());
            clinic.setLat(clinicPayload.getLat());
            clinic.setLon(clinicPayload.getLan());
            clinic.setAddress(clinicPayload.getAddress());
            clinicRepository.save(clinic);

            return result.edit(clinic);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result delete(UUID clinicId) {
        try {
            Clinic clinic = findClinic(clinicId);
            clinicRepository.delete(clinic);
            return result.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result getAll() {
        return result.success(clinicRepository.findAll());
    }

    public Result addDoctor(UUID clinicId, UUID doctorId) {
        try {
            Doctor doctor = doctorService.findDoctor(doctorId);
            Clinic clinic = findClinic(clinicId);
            List<Doctor> doctorList = clinic.getDoctorList();

            doctorList.add(doctor);
            clinic.setDoctorList(doctorList);
            clinicRepository.save(clinic);
            return result.save(clinic);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public Result deleteDoctor(UUID clinicId, UUID doctorId) {
        try {
            Doctor doctor = doctorService.findDoctor(doctorId);
            Clinic clinic = findClinic(clinicId);
            List<Doctor> doctorList = clinic.getDoctorList();
            for (int i = 0; i < doctorList.size(); i++) {
                if (doctorList.get(i).getId().equals(doctor.getId()))
                    doctorList.remove(i);
            }

            clinic.setDoctorList(doctorList);
            clinicRepository.save(clinic);
            return result.save(clinic);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }


    public Result getDoctors(UUID clinicId) {
        return result.success(findClinic(clinicId).getDoctorList());
    }

    public Clinic findClinic(UUID clinicId) {
        return clinicRepository.findById(clinicId).orElseThrow(() -> new ResourceNotFound("cliic", "id", clinicId));
    }
}
