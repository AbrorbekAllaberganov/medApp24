package com.example.medic.payload.doctor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorPayload {
    String fullName;
    String userName;
    String password;
    String phoneNumber;
    String passportSeries;
    String passportNumber;
    String imageId;
    String certificateId;
    String about;
    List<WorkingTimePayload> workingTimePayloadList;
    Double duringTime;
}
