package com.example.sector.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.sector.dao.SectorDao;
import com.example.sector.entity.Sector;

@Service
@EnableTransactionManagement
public class SectorServiceImpl implements SectorService {

	@Autowired
	SectorDao sectorDao;

	@Override
	@Transactional
	public Iterable<Sector> getAllSectors() {

		return sectorDao.findAll();
	}

	@Override
	@Transactional
	public Optional<Sector> getBySectorName(String sectorName) {

		return sectorDao.findBySectorName(sectorName);
	}

	@Override
	@Transactional
	public Optional<Sector> insertUpdate(Sector sector) {

		return Optional.of(sectorDao.save(sector));
	}

	@Override
	@Transactional
	public Iterable<Sector> getAllBySectorNameContaining(String sectorName) {

		return sectorDao.findAllBySectorNameContaining(sectorName);
	}

}
