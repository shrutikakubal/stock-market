package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.StockDao;
import com.example.demo.entity.Stock;

@Service
@EnableTransactionManagement
public class StockServiceImpl implements StockService {
	
	@Autowired
	StockDao stockDao;
	
	@Override
	@Transactional
	public Optional<Stock> getFirstByCompanyCodeOrderByStockDateTimeDesc(String companyCode) {
		
		return stockDao.findFirstByCompanyCodeOrderByStockDateTimeDesc(companyCode);
	}

	@Override
	@Transactional
	public Iterable<Stock> getAll() {
		
		return stockDao.findAllByOrderByStockDateTimeAsc();
	}

	@Override
	@Transactional
	public Iterable<Stock> getAllByCompany(String companyCode) {
		
		return stockDao.findAllByCompanyCodeOrderByStockDateTimeAsc(companyCode);
	}

	@Override
	@Transactional
	public Iterable<Stock> getAllByStockDateTimeBetween(LocalDateTime start, LocalDateTime end) {
		
		return stockDao.findAllByStockDateTimeBetween(start, end);
	}

	@Override
	@Transactional
	public Iterable<Stock> getAllByCompanyCodeAndStockDateTimeBetween(String companyCode, LocalDateTime start,
			LocalDateTime end) {
		
		return stockDao.findAllByCompanyCodeAndStockDateTimeBetween(companyCode, start, end);
	}

	@Override
	@Transactional
	public Iterable<Stock> getAllByCompanyCodeIn(List<String> companyCodeList) {
		
		return stockDao.findAllByCompanyCodeInOrderByStockDateTimeAsc(companyCodeList);
	}

	@Override
	@Transactional
	public Iterable<Stock> getAllByCompanyCodeInAndStockDateTimeBetween(List<String> companyCodeList, LocalDateTime start,
			LocalDateTime end) {
		
		return stockDao.findAllByCompanyCodeInAndStockDateTimeBetween(companyCodeList, start, end);
	}
	
	@Override
	@Transactional
	public Optional<Stock> insertUpdateStock(Stock stock){
		return Optional.of(stockDao.save(stock));
	}
	
	@Override
	@Transactional
	public Optional<Stock> getByCompanyCodeAndStockExchangeCodeAndStockDateTime(String stockCode, String exchange, LocalDateTime ldt){
		return stockDao.findByCompanyCodeAndStockExchangeCodeAndStockDateTime(stockCode, exchange, ldt);
	}
	
	@Override
	@Transactional
	public Iterable<Stock> saveAllStockPrice(List<Stock> stockPriceList){
		return stockDao.saveAll(stockPriceList);
	}
	
	

}
