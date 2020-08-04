package com.example.sector.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.sector.model.CompanyDtls;
import com.example.sector.model.StockDtls;

@FeignClient(name = "company-service")
public interface CompanyClient {

	@RequestMapping(value = "/company/sector/{sectorName}", method = RequestMethod.GET)
	public ResponseEntity<List<CompanyDtls>> getBySector(@PathVariable("sectorName") String sectorName);

	@RequestMapping(value = "/stock/between/stockCodeList", method = RequestMethod.POST)
	public ResponseEntity<List<StockDtls>> getAllStocksInCompaniesBetween(@RequestBody Map<String, ?> params);
	
	@RequestMapping(value = "/stock/stockCodeList", method = RequestMethod.POST)
	public ResponseEntity<List<StockDtls>> getAllStocksByCompanyCodeIn(@RequestBody Map<String, List<String>> params);
}
