package com.example.medic.entity.pharmacy;

import com.example.medic.entity.MyFile;
import com.example.medic.entity.doctor.WorkingTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pharmacy {
    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    protected UUID id;

    String name;
    Double lan;
    Double lat;
    String address;
    String about;

    Double rate;

    @OneToMany(fetch = FetchType.EAGER)
    List<WorkingTime>pharmacyWorkings;

    @OneToOne
    MyFile image;
}
