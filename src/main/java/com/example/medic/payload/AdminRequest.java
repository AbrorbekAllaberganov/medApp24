package com.example.medic.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminRequest {
    private String userName;

    private String fullName;

    private String password;

    private String phoneNumber;

}
