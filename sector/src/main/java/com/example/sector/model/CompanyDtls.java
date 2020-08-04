package com.example.sector.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDtls {
	private String companyName;
	private String companyCode;
	private BigDecimal turnover;
	private String ceo;
	private String boardOfDirectors;
	private String stockExchange;
	private String sectorName;
	private String briefWriteup;
}
