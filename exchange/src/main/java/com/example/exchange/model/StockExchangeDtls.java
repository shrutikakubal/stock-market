package com.example.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockExchangeDtls {

	private String stockExchangeName;
	private String brief;
	private String contactAddress;
	private String remarks;
}
