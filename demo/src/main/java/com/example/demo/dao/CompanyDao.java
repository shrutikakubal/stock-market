package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Company;

@Repository
public interface CompanyDao extends JpaRepository<Company, Integer> {

	Optional<Company> findById(int id);

	Optional<Company> findByCompanyName(String companyName);

	Optional<Company> findByCompanyCode(String companyCode);

	Iterable<Company> findAllBySectorName(String sectorName);

	Iterable<Company> findAllByStockExchange(String stockExchange);

	Iterable<Company> findAllByCompanyNameContaining(String companyName);

	Iterable<Company> findAllByCompanyCodeContaining(String companyCode);
	
	@Transactional
	@Modifying()
	@Query("update Company c set c.status=?2 where c.companyName=?1")
	Optional<Company> setCompanyStatus(String companyName, String status);
}
