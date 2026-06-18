package com.example.reclamos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reclamos.model.Reclamo;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo,Long> {

}
