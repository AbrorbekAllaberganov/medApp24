package com.example.medic.repository;


import com.example.medic.entity.superEntity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParentRepository extends JpaRepository<Parent, UUID> {
    Parent findByUserName (String username);

}
