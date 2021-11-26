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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;
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
    MyFile certifkat;

    @OneToOne
    WorkTime workTime;

    boolean isActive=false;

    String PassportSeries;
    String PassportNumber;

    String about;
}
