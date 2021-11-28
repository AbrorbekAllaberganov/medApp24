package com.example.medic.payload.pharmacy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PharmacyRatePayload {
    UUID userId;
    UUID pharmacyId;
    Double rate;
    String comment;
}
