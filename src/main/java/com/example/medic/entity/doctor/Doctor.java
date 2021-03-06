package com.example.medic.entity.doctor;

import com.example.medic.entity.MyFile;
import com.example.medic.entity.superEntity.Parent;
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
public class Doctor implements Serializable {
    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    protected UUID id;

    @OneToOne
    Parent parent;

    @OneToOne
    MyFile image;

    @OneToOne
    MyFile certificate;

    @OneToMany(fetch = FetchType.EAGER)
    List<WorkingTime> workingTime;

    Double rate;   

    boolean isActive=false;

    String PassportSeries;
    String PassportNumber;

    String about;

    Double duringTime;
}
