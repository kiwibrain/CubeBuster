package com.example.multas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.multas.model.Resena;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
}