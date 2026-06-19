package com.example.cupones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.cupones.model.Resena;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
}