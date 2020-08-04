package com.example.demo.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDtls {
	private String companyName;
	private String companyCode;
	private BigDecimal turnover;
	private String ceo;
	private String boardOfDirectors;
	private String stockExchange;
	private String sectorName;
	private String briefWriteup;
	private String status;

}
