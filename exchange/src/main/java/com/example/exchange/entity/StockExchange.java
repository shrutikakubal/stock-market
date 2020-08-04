package com.example.exchange.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_exchange")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockExchange {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "stock_exchange_name")
	private String stockExchangeName;
	private String brief;
	@Column(name = "contact_address")
	private String contactAddress;
	private String remarks;
}
