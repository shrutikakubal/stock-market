package com.example.sector.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sector.entity.Sector;

@Repository
public interface SectorDao extends JpaRepository<Sector, Integer>{
	
	public Optional<Sector> findBySectorName(String sectorName);
	
	public Iterable<Sector> findAllBySectorNameContaining(String sectorName);
}
