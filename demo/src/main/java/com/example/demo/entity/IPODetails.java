package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Table(name = "ipo_details")
@Entity
public class IPODetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "stock_exchange")
	private String stockExchange;

	@Column(name = "price_per_share")
	private BigDecimal pricePerShare;

	@Column(name = "total_number_of_shares")
	private Long totalNumberOfShares;

	@Column(name = "open_date_time")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime openDateTime;

	private String remarks;

	@Override
	public String toString() {
		return "IPODetails [id=" + id + ", companyName=" + companyName + ", stockExchange=" + stockExchange
				+ ", pricePerShare=" + pricePerShare + ", totalNumberOfShares=" + totalNumberOfShares
				+ ", openDateTime=" + openDateTime + ", remarks=" + remarks + "]";
	}

	public IPODetails() {

	}

	public IPODetails(String companyName, String stockExchange, BigDecimal pricePerShare, Long totalNumberOfShares,
			LocalDateTime openDateTime, String remarks) {
		this.companyName = companyName;
		this.stockExchange = stockExchange;
		this.pricePerShare = pricePerShare;
		this.totalNumberOfShares = totalNumberOfShares;
		this.openDateTime = openDateTime;
		this.remarks = remarks;
	}

}
