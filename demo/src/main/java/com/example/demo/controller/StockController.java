package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Stock;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.StockDtls;
import com.example.demo.service.StockService;

@RestController
@RequestMapping("/stock")
@CrossOrigin
public class StockController {

	@Autowired
	StockService stockService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<StockDtls>> getAllStocks() {
		Iterable<Stock> stockList = stockService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(convertToDtoList(stockList));
	}

	@RequestMapping(value = "/current/{companyCode}", method = RequestMethod.GET)
	public ResponseEntity<StockDtls> getCurrent(@PathVariable("companyCode") String companyCode) {
		Stock stock = stockService.getFirstByCompanyCodeOrderByStockDateTimeDesc(companyCode)
				.orElseThrow(() -> new ResourceNotFoundException("No such company code " + companyCode));
		return ResponseEntity.status(HttpStatus.FOUND).body(convertToDto(stock));
	}

	@RequestMapping(value = "/all/{companyCode}", method = RequestMethod.GET)
	public ResponseEntity<List<StockDtls>> getAllStocksByCompany(@PathVariable("companyCode") String companyCode)
			throws ResourceNotFoundException {
		Iterable<Stock> stockList = stockService.getAllByCompany(companyCode);
		return ResponseEntity.status(HttpStatus.FOUND).body(convertToDtoList(stockList));
	}

	@RequestMapping(value = "/between/{companyCode}", method = RequestMethod.POST)
	public ResponseEntity<List<StockDtls>> getAllStocksBetween(
			@PathVariable("companyCode") String companyCode, @RequestBody Map<String, String> params) {
		LocalDateTime start = LocalDateTime.parse((CharSequence) params.get("start"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime end = LocalDateTime.parse((CharSequence) params.get("end"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Iterable<Stock> stockList;
		if (companyCode != null) {
			stockList = stockService.getAllByCompanyCodeAndStockDateTimeBetween(companyCode, start, end);
		} else {
			stockList = stockService.getAllByStockDateTimeBetween(start, end);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(convertToDtoList(stockList));
	}

	@RequestMapping(value = "/stockCodeList", method = RequestMethod.POST)
	public ResponseEntity<List<StockDtls>> getAllStocksByCompanyCodeIn(@RequestBody Map<String, List<String>> params) {
		List<String> stockCodeList = (List<String>) params.get("stockCodeList");
		Iterable<Stock> stockList = stockService.getAllByCompanyCodeIn(stockCodeList);
		return ResponseEntity.status(HttpStatus.OK).body(convertToDtoList(stockList));
	}

	@RequestMapping(value = "/between/stockCodeList", method = RequestMethod.POST)
	public ResponseEntity<List<StockDtls>> getAllStocksInCompaniesBetween(@RequestBody Map<String, ?> params) {
		LocalDateTime start = LocalDateTime.parse((CharSequence) params.get("start"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime end = LocalDateTime.parse((CharSequence) params.get("end"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		List<String> stockCodeList = (List<String>) params.get("stockCodeList");
		Iterable<Stock> stockList = stockService.getAllByCompanyCodeInAndStockDateTimeBetween(stockCodeList, start,
				end);
		return ResponseEntity.status(HttpStatus.FOUND).body(convertToDtoList(stockList));
	}

	@PostMapping("/new")
	public StockDtls createStock(@RequestBody StockDtls stockDtls) {
		Stock stock = (new ModelMapper()).map(stockDtls, Stock.class);
		Optional<Stock> s = stockService.insertUpdateStock(stock);
		stockDtls = convertToDto(s.get());
		return stockDtls;
	}

	@PostMapping("/new/stocklist")
	public List<StockDtls> createManyStocks(@RequestBody List<StockDtls> stockDtlsList) {
		List<Stock> stockPriceList = stockDtlsList.stream()
				.map(stockDtls -> new ModelMapper().map(stockDtls, Stock.class)).collect(Collectors.toList());
		stockService.saveAllStockPrice(stockPriceList);
		return stockDtlsList;
	}

	@RequestMapping(value = "/checkstock", method = RequestMethod.POST)
	public StockDtls checkStock(@RequestBody Map<String, String> params) {
		String stockCode = (String) params.get("companyCode");
		String exchange = (String) params.get("stockExchange");
		LocalDateTime ldt = LocalDateTime.parse((CharSequence) params.get("stockDateTime"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Optional<Stock> stock = stockService.getByCompanyCodeAndStockExchangeCodeAndStockDateTime(stockCode, exchange, ldt);
		if(stock.isPresent()) {
		return convertToDto(stock.get());
	}
			return null;
	}
	private StockDtls convertToDto(Stock stock) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(stock, StockDtls.class);
	}

	private List<StockDtls> convertToDtoList(Iterable<Stock> stockList) {
		List<StockDtls> stockDtlsList = new ArrayList<StockDtls>();
		for (Stock s : stockList) {
			stockDtlsList.add(convertToDto(s));
		}
		return stockDtlsList;
	}

}
