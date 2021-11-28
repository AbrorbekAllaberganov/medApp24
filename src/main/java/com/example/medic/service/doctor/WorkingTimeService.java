package com.example.medic.service.doctor;

import com.example.medic.entity.doctor.WorkingTime;
import com.example.medic.payload.doctor.WorkingTimePayload;
import com.example.medic.repository.doctor.WorkingTimeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class WorkingTimeService {
    private final WorkingTimeRepository workingTimeRepository;
    private final Logger logger= LoggerFactory.getLogger(WorkingTimeService.class);

    public WorkingTime save(WorkingTimePayload workingTimePayload) {
        try {
            WorkingTime workingTime = new WorkingTime();
            workingTime.setWorkDay(workingTimePayload.getWorkDay());
            workingTime.setEndTime(LocalTime.parse(workingTimePayload.getEndTime()));
            workingTime.setStartTime(LocalTime.parse(workingTimePayload.getStartTime()));

            return workingTimeRepository.save(workingTime);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
