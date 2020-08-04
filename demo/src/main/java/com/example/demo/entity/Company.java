package com.example.demo.entity;

import java.math.BigDecimal;

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
@Table(name="company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	 @Column(name = "company_name")
	  private String companyName;
	 
	 @Column(name = "company_code")
	  private String companyCode;
	 
	 @Column(name="turnover")
	 private BigDecimal turnover;
	 
	 @Column(name="ceo") 
	  private String ceo;
	  
	  @Column(name = "board_of_directors")
	  private String boardOfDirectors;
	  
	  @Column(name = "stock_exchange")
	  private String stockExchange;
	  
	  @Column(name = "sector_name")
	  private String sectorName;
	  
	  @Column(name = "brief_writeup")
	  private String briefWriteup;
	  
	  @Column(name = "status")
	  private String status;
	  
}
