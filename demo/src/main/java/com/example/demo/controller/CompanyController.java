package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Company;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.CompanyDtls;
import com.example.demo.service.CompanyService;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@GetMapping("/all")
	public ResponseEntity<List<CompanyDtls>> getAllCompanies() {
		Iterable<Company> companyList = companyService.getAll();
		List<CompanyDtls> companyDtlsList = new ArrayList<CompanyDtls>();
		for (Company c : companyList) {
			companyDtlsList.add(convertToDto(c));
		}
		return ResponseEntity.status(HttpStatus.OK).body(companyDtlsList);
	}

	@GetMapping("/name/{companyName}")
	public ResponseEntity<CompanyDtls> getByName(@PathVariable("companyName") String companyName){

		Optional<Company> company = companyService.getCompanyByCompanyName(companyName);
		CompanyDtls companyDtls = convertToDto(company.orElseThrow(() -> new ResourceNotFoundException("No such company "+companyName)));
		logger.info("company name {}",companyName);
		return ResponseEntity.status(HttpStatus.FOUND).body(companyDtls);
	}

	@GetMapping("/code/{stockCode}")
	public ResponseEntity<CompanyDtls> getByStockCode(@PathVariable("stockCode") String stockCode) {
		Company company = (companyService.getCompanyByCompanyCode(stockCode)).orElseThrow(() -> new ResourceNotFoundException("No such company with code "+stockCode));
		CompanyDtls companyDtls = convertToDto(company);
		return ResponseEntity.status(HttpStatus.FOUND).body(companyDtls);
	}

	@GetMapping("/sector/{sectorName}")
	public ResponseEntity<List<CompanyDtls>> getBySector(@PathVariable("sectorName") String sectorName) {
		Iterable<Company> companyList = companyService.getAllBySectorName(sectorName);
		List<CompanyDtls> companyDtlsList = new ArrayList<CompanyDtls>();
		for (Company c : companyList) {
			companyDtlsList.add(convertToDto(c));
		}
		return ResponseEntity.status(HttpStatus.OK).body(companyDtlsList);
	}

	@GetMapping("/exchange/{stockExchange}")
	public ResponseEntity<List<CompanyDtls>> getByStockExchange(@PathVariable("stockExchange") String stockExchange) {
		Iterable<Company> companyList = companyService.getAllByStockExchange(stockExchange);
		List<CompanyDtls> companyDtlsList = new ArrayList<CompanyDtls>();
		for (Company c : companyList) {
			companyDtlsList.add(convertToDto(c));
		}
		return ResponseEntity.status(HttpStatus.OK).body(companyDtlsList);
	}

	@GetMapping("/findByName/{name}")
	public ResponseEntity<List<CompanyDtls>> findCompanyContainingName(@PathVariable("name") String name) {
		Iterable<Company> companyList = companyService.getAllByCompanyNameContaining(name);
		List<CompanyDtls> companyDtlsList = new ArrayList<CompanyDtls>();
		for (Company c : companyList) {
			companyDtlsList.add(convertToDto(c));
		}
		return ResponseEntity.status(HttpStatus.OK).body(companyDtlsList);
	}

	@GetMapping("/findByStockCode/{code}")
	public ResponseEntity<List<CompanyDtls>> findCompanyContainingCode(@PathVariable("code") String code) {
		Iterable<Company> companyList = companyService.getAllByCompanyCodeContaining(code);
		List<CompanyDtls> companyDtlsList = new ArrayList<CompanyDtls>();
		for (Company c : companyList) {
			companyDtlsList.add(convertToDto(c));
		}
		return ResponseEntity.status(HttpStatus.OK).body(companyDtlsList);
	}

	@PostMapping("/admin/new")
	public ResponseEntity<CompanyDtls> createCompany(@RequestBody CompanyDtls companyDtls) {
		Company company = (new ModelMapper()).map(companyDtls, Company.class);
		Optional<Company> c = companyService.insertUpdateCompany(company);
		companyDtls = convertToDto(c.get());
		return ResponseEntity.status(HttpStatus.CREATED).body(companyDtls);
	}
	
	@PutMapping("/admin/update/{companyName}")
	public ResponseEntity<CompanyDtls> updateCompany(@PathVariable("companyName") String companyName, @RequestBody CompanyDtls companyDtls){
		Company company = companyService.getCompanyByCompanyName(companyName).orElseThrow(() -> new ResourceNotFoundException("No such company "+companyName));
		new ModelMapper().map(companyDtls, company);
		Optional<Company> c = companyService.insertUpdateCompany(company);
		companyDtls = convertToDto(c.get());
		return ResponseEntity.status(HttpStatus.OK).body(companyDtls);
	}
	
	@PutMapping("/admin/activate/{companyName}")
	public ResponseEntity<CompanyDtls> updateCompanyActive(@PathVariable("companyName") String companyName, @RequestParam(name="status") String status){
		Company company = companyService.setCompanyStatus(companyName, status).orElseThrow(() -> new ResourceNotFoundException("No such company "+companyName));
		return ResponseEntity.status(HttpStatus.OK).body(convertToDto(company));
	}

	private CompanyDtls convertToDto(Company company) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		CompanyDtls companyDtls = modelMapper.map(company, CompanyDtls.class);
		return companyDtls;

	}

}
