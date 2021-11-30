package com.example.medic.service.doctor;

import com.example.medic.entity.User;
import com.example.medic.entity.doctor.Doctor;
import com.example.medic.entity.doctor.DoctorBooking;
import com.example.medic.entity.doctor.WorkingTime;
import com.example.medic.payload.Result;
import com.example.medic.payload.doctor.DoctorBookingPayload;
import com.example.medic.payload.doctor.DoctorPayload;
import com.example.medic.repository.doctor.DoctorBookingRepository;
import com.example.medic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DoctorBookingService {
    private final DoctorBookingRepository doctorBookingRepository;
    private final DoctorService doctorService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(DoctorBookingService.class);
    Result result = new Result();

    public Result save(DoctorBookingPayload doctorBookingPayload) {
        try {
            DoctorBooking doctorBooking = new DoctorBooking();
            Doctor doctor = doctorService.findDoctor(doctorBookingPayload.getDoctorId());
            User user = userService.findUser(doctorBookingPayload.getUserId());
            SimpleDateFormat spf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            doctorBooking.setDoctor(doctor);
            doctorBooking.setUser(user);
            doctorBooking.setStartTime(spf.parse(doctorBookingPayload.getStartDate()));
            doctorBooking.setEndTIme(new Date((long) (doctorBooking.getStartTime().getTime() + doctor.getDuringTime() * 60000)));

            if (checkDoctor(doctor, doctorBooking.getStartTime()))
                if (doctorBookingCheck(doctor, doctorBooking)) {
                    doctorBookingRepository.save(doctorBooking);
                    return result.save(doctorBooking);
                } else {
                    return new Result("Bu vaqt band", false);
                }
            else
                return new Result("Vaqt xato kiritildi", false);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return result.error(e);
        }
    }

    public boolean checkDoctor(Doctor doctor, Date startDate) {
        List<WorkingTime> workingTimeList = doctor.getWorkingTime();
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(startDate);
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        Instant instant = Instant.ofEpochMilli(calendar.getTimeInMillis());
        LocalTime startTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        LocalTime endTime = startTime.plusMinutes(doctor.getDuringTime().longValue());

        for (WorkingTime workingTime : workingTimeList) {
            if (workingTime.getWorkDay() == day) {
                LocalTime start = workingTime.getStartTime();
                LocalTime end = workingTime.getEndTime();

                if (start.compareTo(startTime) != 1 && end.compareTo(endTime) != -1)
                    return true;
            }
        }

        return false;
    }

    public boolean doctorBookingCheck(Doctor doctor, DoctorBooking doctorBooking) {
        List<DoctorBooking> doctorBookingList = doctorBookingRepository.getAllByDoctor_Id(doctor.getId());
        if (doctorBookingList.isEmpty())
            return true;
        else {
            List<DoctorBooking> doctorBookings = doctorBookingRepository.getAll(doctorBooking.getEndTIme(), doctorBooking.getStartTime());
            return doctorBookings.size()==0;
        }
    }

}
