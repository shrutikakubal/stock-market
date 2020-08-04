package com.example.exchange.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.exchange.model.CompanyDtls;

@FeignClient(name="company-service")
public interface CompanyClient {
	@RequestMapping(value="/company/exchange/{stockExchange}", method=RequestMethod.GET)
	ResponseEntity<List<CompanyDtls>> getByStockExchange(@PathVariable("stockExchange") String stockExchange);
}
