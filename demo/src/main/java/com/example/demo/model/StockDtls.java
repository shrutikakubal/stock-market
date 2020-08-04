package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class StockDtls {
	private String companyCode;

	private String stockExchangeCode;

	private BigDecimal currentPrice;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime stockDateTime;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getStockExchangeCode() {
		return stockExchangeCode;
	}

	public void setStockExchangeCode(String stockExchangeCode) {
		this.stockExchangeCode = stockExchangeCode;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public LocalDateTime getStockDateTime() {
		return stockDateTime;
	}

	public void setStockDateTime(LocalDateTime stockDateTime) {
		this.stockDateTime = stockDateTime;
	}

	public StockDtls(String companyCode, String stockExchangeCode, BigDecimal currentPrice,
			LocalDateTime stockDateTime) {
		this.companyCode = companyCode;
		this.stockExchangeCode = stockExchangeCode;
		this.currentPrice = currentPrice;
		this.stockDateTime = stockDateTime;
	}

	public StockDtls() {

	}

}
