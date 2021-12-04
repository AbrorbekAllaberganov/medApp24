package com.example.medic.entity.doctor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Clinic implements Serializable {
    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    protected UUID id;

    String name;

    String address;

    Double lon;
    Double lat;

    @OneToMany(fetch = FetchType.EAGER)
    List<Doctor>doctorList;


}
