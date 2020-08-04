package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class IPODtls {
	private String companyName;
	private String stockExchange;
	private BigDecimal pricePerShare;
	private Long totalNumberOfShares;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime openDateTime;

	private String remarks;

	public IPODtls() {

	}

	public IPODtls(String companyName, String stockExchange, BigDecimal pricePerShare, Long totalNumberOfShares,
			LocalDateTime openDateTime, String remarks) {
		this.companyName = companyName;
		this.stockExchange = stockExchange;
		this.pricePerShare = pricePerShare;
		this.totalNumberOfShares = totalNumberOfShares;
		this.openDateTime = openDateTime;
		this.remarks = remarks;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	public BigDecimal getPricePerShare() {
		return pricePerShare;
	}

	public void setPricePerShare(BigDecimal pricePerShare) {
		this.pricePerShare = pricePerShare;
	}

	public Long getTotalNumberOfShares() {
		return totalNumberOfShares;
	}

	public void setTotalNumberOfShares(Long totalNumberOfShares) {
		this.totalNumberOfShares = totalNumberOfShares;
	}

	public LocalDateTime getOpenDateTime() {
		return openDateTime;
	}

	public void setOpenDateTime(LocalDateTime openDateTime) {
		this.openDateTime = openDateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
