package com.example.medic.repository.doctor;

import com.example.medic.entity.doctor.DoctorBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorBookingRepository extends JpaRepository<DoctorBooking, UUID> {
    @Query(value = "select * from doctor_booking where start_time<:endTime and endtime>:startTime",nativeQuery = true)
    List<DoctorBooking> getAll(@Param("endTime")Date endTime,@Param("startTime")Date startTime);

    List<DoctorBooking> getAllByDoctor_Id(UUID doctorId);
}
