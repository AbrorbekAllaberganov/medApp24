package com.example.medic.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorRatePayload {
    private UUID userId;
    private UUID doctorId;
    private Double rate;
    private String comment;
}
