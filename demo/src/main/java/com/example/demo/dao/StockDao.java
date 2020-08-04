package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Stock;

@Repository
public interface StockDao extends JpaRepository<Stock, Integer> {

	Optional<Stock> findFirstByCompanyCodeOrderByStockDateTimeDesc(String companyCode);

	Iterable<Stock> findAllByOrderByStockDateTimeAsc();

	Iterable<Stock> findAllByCompanyCodeOrderByStockDateTimeAsc(String companyCode);

	Iterable<Stock> findAllByStockDateTimeBetween(LocalDateTime start, LocalDateTime end);

	Iterable<Stock> findAllByCompanyCodeAndStockDateTimeBetween(String companyCode, LocalDateTime start,
			LocalDateTime end);

	Iterable<Stock> findAllByCompanyCodeInOrderByStockDateTimeAsc(List<String> companyCodeList);

	Iterable<Stock> findAllByCompanyCodeInAndStockDateTimeBetween(List<String> companyCodeList, LocalDateTime start,
			LocalDateTime end);
	
	Optional<Stock> findByCompanyCodeAndStockExchangeCodeAndStockDateTime(String stockCode, String exchange, LocalDateTime ldt);

}
