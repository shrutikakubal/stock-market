package com.example.sector.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.sector.entity.Sector;
import com.example.sector.exception.ResourceNotFoundException;
import com.example.sector.feign.CompanyClient;
import com.example.sector.model.CompanyDtls;
import com.example.sector.model.SectorDtls;
import com.example.sector.model.StockDtls;
import com.example.sector.service.SectorService;

@RestController
@RequestMapping("/sector")
@CrossOrigin
public class SectorController {

	@Autowired
	SectorService sectorService;
	
	@Autowired
	CompanyClient companyClient;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<SectorDtls>> getAllSectors() {
		return ResponseEntity.status(HttpStatus.OK).body(convertToDtoList(sectorService.getAllSectors()));
	}
	
	@RequestMapping(value = "/name/{sectorName}", method = RequestMethod.GET)
	public ResponseEntity<SectorDtls> getSectorByName(@PathVariable("sectorName") String sectorName){
		Sector sector = sectorService.getBySectorName(sectorName).orElseThrow(() -> new ResourceNotFoundException("No such sector "+sectorName));
		return ResponseEntity.status(HttpStatus.FOUND).body(convertToDto(sector));
	}
	
	@RequestMapping(value = "/admin/new", method = RequestMethod.POST)
	public ResponseEntity<SectorDtls> newSector(@RequestBody SectorDtls sectorDtls){
		Sector sector = new ModelMapper().map(sectorDtls, Sector.class);
		return ResponseEntity.status(HttpStatus.OK).body(convertToDto(sectorService.insertUpdate(sector).get()));
	}
	
	@RequestMapping(value = "/admin/update/{sectorName}", method = RequestMethod.PUT)
	public ResponseEntity<SectorDtls> updateSectorByName(@PathVariable("sectorName") String sectorName, @RequestBody SectorDtls sectorDtls){
		Sector sector = sectorService.getBySectorName(sectorName).orElseThrow(() -> new ResourceNotFoundException("No such sector "+sectorName));
		new ModelMapper().map(sectorDtls, sector);
		sector = sectorService.insertUpdate(sector).get();
		return ResponseEntity.status(HttpStatus.OK).body(convertToDto(sector));
	}
	
	@RequestMapping(value = "/find/{sectorName}/companyList", method = RequestMethod.GET)
	public ResponseEntity<List<CompanyDtls>> findCompaniesBySector(@PathVariable("sectorName") String sectorName){
		return companyClient.getBySector(sectorName);
	}
	
	@RequestMapping(value = "/sectorPrice/{sectorName}", method = RequestMethod.GET)
	public ResponseEntity<List<SectorDtls>> findSectorPrice(@PathVariable("sectorName") String sectorName){
		Sector sector = sectorService.getBySectorName(sectorName).orElseThrow(() -> new ResourceNotFoundException("No such sector "+ sectorName));
		List<CompanyDtls> companyDtlsList = companyClient.getBySector(sectorName).getBody();
		List<String> stockCodeList = companyDtlsList.stream().map(CompanyDtls::getCompanyCode).collect(Collectors.toList());
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		params.put("stockCodeList", stockCodeList);
		List<StockDtls> stockDtlsList = (List<StockDtls>)companyClient.getAllStocksByCompanyCodeIn(params).getBody();
		Map<LocalDateTime, List<StockDtls>> group = stockDtlsList.stream().collect(Collectors.groupingBy(StockDtls::getStockDateTime));
		List<SectorDtls> sectorList = new ArrayList<SectorDtls>();
		for(Map.Entry<LocalDateTime, List<StockDtls>> entry : group.entrySet()) {
			SectorDtls sectorDtls = convertToDto(sector);
			double sectorPrice = entry.getValue().stream().map(StockDtls::getCurrentPrice).mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
			sectorDtls.setSectorDateTime(entry.getKey());
			sectorDtls.setSectorPrice(sectorPrice);
			sectorList.add(sectorDtls);
		}
		sectorList.sort((s1, s2) -> (s1.getSectorDateTime()).compareTo(s2.getSectorDateTime()));
		return ResponseEntity.status(HttpStatus.OK).body(sectorList);
	}
	
	@RequestMapping(value="/sectorPriceBetween/{sectorName}", method = RequestMethod.POST)
	public ResponseEntity<List<SectorDtls>> findSectorPriceBetween(@PathVariable("sectorName") String sectorName, @RequestBody Map<String, ?> param){
		Sector sector = sectorService.getBySectorName(sectorName).orElseThrow(() -> new ResourceNotFoundException("No such sector "+ sectorName));
		List<CompanyDtls> companyDtlsList = companyClient.getBySector(sectorName).getBody();
		List<String> stockCodeList = companyDtlsList.stream().map(CompanyDtls::getCompanyCode).collect(Collectors.toList());
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		params.put("stockCodeList", stockCodeList);
		List<StockDtls> stockDtlsList = new ArrayList<StockDtls>();
		if(param.size()==1) {
			stockDtlsList = (List<StockDtls>)companyClient.getAllStocksByCompanyCodeIn(params).getBody();
	}
		if(param.size()>1) {
			stockDtlsList = (List<StockDtls>)companyClient.getAllStocksInCompaniesBetween(params).getBody();
		}
		Map<LocalDateTime, List<StockDtls>> group = stockDtlsList.stream().collect(Collectors.groupingBy(StockDtls::getStockDateTime));
		List<SectorDtls> sectorList = new ArrayList<SectorDtls>();
		for(Map.Entry<LocalDateTime, List<StockDtls>> entry : group.entrySet()) {
			SectorDtls sectorDtls = convertToDto(sector);
			double sectorPrice = entry.getValue().stream().map(StockDtls::getCurrentPrice).mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
			sectorDtls.setSectorDateTime(entry.getKey());
			sectorDtls.setSectorPrice(sectorPrice);
			sectorList.add(sectorDtls);
		}
		sectorList.sort((s1, s2) -> (s1.getSectorDateTime()).compareTo(s2.getSectorDateTime()));
		return ResponseEntity.status(HttpStatus.OK).body(sectorList);
		
	}
	
	private SectorDtls convertToDto(Sector sector) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(sector, SectorDtls.class);
	}

	private List<SectorDtls> convertToDtoList(Iterable<Sector> sectorList) {
		List<SectorDtls> sectorDtlsList = new ArrayList<SectorDtls>();
		for (Sector s : sectorList) {
			sectorDtlsList.add(convertToDto(s));
		}
		return sectorDtlsList;
	}
}
