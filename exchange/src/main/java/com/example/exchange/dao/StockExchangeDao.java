package com.example.exchange.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exchange.entity.StockExchange;

@Repository
public interface StockExchangeDao extends JpaRepository<StockExchange, Integer> {
	
	public Optional<StockExchange> findByStockExchangeName(String stockExchangeName);
}
