package com.example.exchange.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.example.exchange.entity.StockExchange;
import com.example.exchange.exception.ResourceNotFoundException;
import com.example.exchange.feign.CompanyClient;
import com.example.exchange.model.CompanyDtls;
import com.example.exchange.model.StockExchangeDtls;
import com.example.exchange.service.StockExchangeService;

@RestController
@RequestMapping("/exchange")
@CrossOrigin
public class StockExchangeController {

	@Autowired
	StockExchangeService stockExchangeService;
	
	@Autowired
	CompanyClient companyClient;
	

	public StockExchangeController(StockExchangeService stockExchangeService, CompanyClient companyClient) {
		this.stockExchangeService = stockExchangeService;
		this.companyClient = companyClient;
	}

	@RequestMapping(value = "/admin/all", method = RequestMethod.GET)
	public ResponseEntity<List<StockExchangeDtls>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(convertToDtoList(stockExchangeService.getAllStockExchanges()));
	}

	@RequestMapping(value = "/admin/name/{StockExchangeName}", method = RequestMethod.GET)
	public ResponseEntity<StockExchangeDtls> getByName(@PathVariable("StockExchangeName") String stockExchangeName) {
		StockExchange stockExchange = stockExchangeService.getByStockExchangeName(stockExchangeName)
				.orElseThrow(() -> new ResourceNotFoundException("No such Stock Exchange " + stockExchangeName));
		return ResponseEntity.status(HttpStatus.FOUND).body(convertToDto(stockExchange));
	}

	@RequestMapping(value = "/admin/id/{StockExchangeId}", method = RequestMethod.GET)
	public ResponseEntity<StockExchangeDtls> getById(@PathVariable("StockExchangeId") int stockExchangeId) {
		StockExchange stockExchange = stockExchangeService.getByStockExchangeId(stockExchangeId)
				.orElseThrow(() -> new ResourceNotFoundException("No such Stock Exchange with ID " + stockExchangeId));
		return ResponseEntity.status(HttpStatus.FOUND).body(convertToDto(stockExchange));
	}

	@RequestMapping(value = "/admin/new", method = RequestMethod.POST)
	public ResponseEntity<StockExchangeDtls> addNew(@RequestBody StockExchangeDtls stockExchangeDtls) {

		StockExchange stockExchange = new ModelMapper().map(stockExchangeDtls, StockExchange.class);
		stockExchange = stockExchangeService.insertUpdate(stockExchange).get();
		return ResponseEntity.status(HttpStatus.OK).body(convertToDto(stockExchange));
	}

	@RequestMapping(value = "/admin/update/{stockExchangeName}", method = RequestMethod.PUT)
	public ResponseEntity<StockExchangeDtls> update(@PathVariable("stockExchangeName") String stockExchangeName,
			@RequestBody StockExchangeDtls stockExchangeDtls) {
		StockExchange stockExchange = stockExchangeService.getByStockExchangeName(stockExchangeName)
				.orElseThrow(() -> new ResourceNotFoundException("No such Stock Exchange " + stockExchangeName));
		new ModelMapper().map(stockExchangeDtls, stockExchange);
		stockExchange = stockExchangeService.insertUpdate(stockExchange).get();
		return ResponseEntity.status(HttpStatus.OK).body(convertToDto(stockExchange));
	}
	
	@RequestMapping(value="/find/{stockExchangeName}/companylist", method=RequestMethod.GET)
	public ResponseEntity<List<CompanyDtls>> findCompanyList(@PathVariable("stockExchangeName") String stockExchangeName){
		return companyClient.getByStockExchange(stockExchangeName);
	}
	

	private StockExchangeDtls convertToDto(StockExchange stockExchange) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(stockExchange, StockExchangeDtls.class);
	}

	private List<StockExchangeDtls> convertToDtoList(Iterable<StockExchange> stockExchangeList) {
		List<StockExchangeDtls> stockExchangeDtlsList = new ArrayList<StockExchangeDtls>();
		for (StockExchange s : stockExchangeList) {
			stockExchangeDtlsList.add(convertToDto(s));
		}
		return stockExchangeDtlsList;
	}
}
