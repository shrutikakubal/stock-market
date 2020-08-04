package com.example.sector.service;

import java.util.Optional;

import com.example.sector.entity.Sector;

public interface SectorService {
	
	Iterable<Sector> getAllSectors();
	Optional<Sector> getBySectorName(String sectorName);
	Optional<Sector> insertUpdate(Sector sector);
	Iterable<Sector> getAllBySectorNameContaining(String sectorName);

}
