package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Stock;

public interface StockService {
	Optional<Stock> getFirstByCompanyCodeOrderByStockDateTimeDesc(String companyCode);

	Iterable<Stock> getAll();

	Iterable<Stock> getAllByCompany(String companyCode);

	Iterable<Stock> getAllByStockDateTimeBetween(LocalDateTime start, LocalDateTime end);

	Iterable<Stock> getAllByCompanyCodeAndStockDateTimeBetween(String companyCode, LocalDateTime start,
			LocalDateTime end);

	Iterable<Stock> getAllByCompanyCodeIn(List<String> stockCodeList);

	Iterable<Stock> getAllByCompanyCodeInAndStockDateTimeBetween(List<String> stockCodeList, LocalDateTime start,
			LocalDateTime end);

	//for uploading excel
	Optional<Stock> insertUpdateStock(Stock stock);
	
	Optional<Stock> getByCompanyCodeAndStockExchangeCodeAndStockDateTime(String stockCode, String exchange, LocalDateTime ldt);
	
	Iterable<Stock> saveAllStockPrice(List<Stock> stockPriceList);
}
