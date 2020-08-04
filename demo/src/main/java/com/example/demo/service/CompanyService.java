package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.Company;

public interface CompanyService {

	Optional<Company> getCompanyById(int id);

	Optional<Company> getCompanyByCompanyName(String companyName);

	Optional<Company> getCompanyByCompanyCode(String companyCode);

	Iterable<Company> getAll();

	Iterable<Company> getAllBySectorName(String sectorName);

	Iterable<Company> getAllByStockExchange(String stockExchange);

	Iterable<Company> getAllByCompanyNameContaining(String companyName);

	Iterable<Company> getAllByCompanyCodeContaining(String companyCode);

	Optional<Company> insertUpdateCompany(Company company);
	
	Optional<Company> setCompanyStatus(String companyName, String active);
}
