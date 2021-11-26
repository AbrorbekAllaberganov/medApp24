package com.example.medic.repository;

import com.example.medic.entity.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MyFileRepository extends JpaRepository<MyFile, UUID> {


    boolean deleteByHashId(String hashId);

    MyFile findByHashId(String hashId);
}
