package com.example.cupones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cupones.model.Reclamo;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo,Long> {

}
