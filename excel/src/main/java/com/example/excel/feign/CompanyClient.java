package com.example.excel.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.excel.model.StockDtls;

@FeignClient(name="company-service")
public interface CompanyClient {
	
	@RequestMapping(value="/stock/new", method=RequestMethod.POST)
	public StockDtls createStock(@RequestBody StockDtls stockDtls); 
	
	@RequestMapping(value="/stock/new/stocklist", method=RequestMethod.POST)
	public List<StockDtls> createManyStocks(@RequestBody List<StockDtls> stockDtlsList);
	
	@RequestMapping(value = "/stock/checkstock", method = RequestMethod.POST)
	public StockDtls checkStock(@RequestBody Map<String, String> params);
}
