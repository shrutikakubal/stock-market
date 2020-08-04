package com.example.exchange.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.exchange.dao.StockExchangeDao;
import com.example.exchange.entity.StockExchange;

@Service
@EnableTransactionManagement
public class StockExchangeServiceImpl implements StockExchangeService {

	@Autowired
	StockExchangeDao stockExchangeDao;

	@Override
	@Transactional
	public Iterable<StockExchange> getAllStockExchanges() {

		return stockExchangeDao.findAll();
	}

	@Override
	@Transactional
	public Optional<StockExchange> getByStockExchangeName(String stockExchangeName) {

		return stockExchangeDao.findByStockExchangeName(stockExchangeName);
	}

	@Override
	@Transactional
	public Optional<StockExchange> getByStockExchangeId(int id) {

		return stockExchangeDao.findById(id);
	}

	@Override
	@Transactional
	public Optional<StockExchange> insertUpdate(StockExchange stockExchange) {

		return Optional.of(stockExchangeDao.save(stockExchange));
	}

}
