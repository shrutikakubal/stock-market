package com.example.exchange.service;

import java.util.Optional;

import com.example.exchange.entity.StockExchange;


public interface StockExchangeService {
	
	Iterable<StockExchange> getAllStockExchanges();

	Optional<StockExchange> getByStockExchangeName(String stockExchangeName);

	Optional<StockExchange> getByStockExchangeId(int id);

	Optional<StockExchange> insertUpdate(StockExchange stockExchange);
}
