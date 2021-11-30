package com.example.medic.payload.pharmacy;

import com.example.medic.payload.doctor.WorkingTimePayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PharmacyPayload {
    String name;
    Double lan;
    Double lat;
    String address;
    String about;
    List<WorkingTimePayload>workingTimePayloadList;
    String imageHashId;
//    List<UUID>medicineIds;
}
