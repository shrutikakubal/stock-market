package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.CompanyDao;
import com.example.demo.entity.Company;

@Service
@EnableTransactionManagement
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	@Override
	@Transactional
	public Optional<Company> getCompanyById(int id) {

		return companyDao.findById(id);
	}

	@Override
	@Transactional
	public Optional<Company> getCompanyByCompanyName(String companyName) {

		return companyDao.findByCompanyName(companyName);
	}

	@Override
	@Transactional
	public Optional<Company> getCompanyByCompanyCode(String companyCode) {

		return companyDao.findByCompanyCode(companyCode);
	}

	@Override
	@Transactional
	public Iterable<Company> getAll() {

		return companyDao.findAll();
	}

	@Override
	@Transactional
	public Iterable<Company> getAllBySectorName(String sectorName) {

		return companyDao.findAllBySectorName(sectorName);
	}

	@Override
	@Transactional
	public Iterable<Company> getAllByStockExchange(String stockExchange) {

		return companyDao.findAllByStockExchange(stockExchange);
	}

	@Override
	@Transactional
	public Iterable<Company> getAllByCompanyNameContaining(String companyName) {

		return companyDao.findAllByCompanyNameContaining(companyName);
	}

	@Override
	@Transactional
	public Iterable<Company> getAllByCompanyCodeContaining(String companyCode) {

		return companyDao.findAllByCompanyCodeContaining(companyCode);
	}

	@Override
	@Transactional
	public Optional<Company> insertUpdateCompany(Company company) {
		companyDao.save(company);
		return Optional.of(company);
	}
	
	@Override
	@Transactional
	public Optional<Company> setCompanyStatus(String companyName, String active){
		return companyDao.setCompanyStatus(companyName, active);
	}

}
