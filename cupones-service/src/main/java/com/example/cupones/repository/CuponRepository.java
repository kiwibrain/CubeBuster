package com.example.cupones.repository;

import com.example.cupones.model.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Long> {
}