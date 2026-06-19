package com.example.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.pagos.model.Resena;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
}