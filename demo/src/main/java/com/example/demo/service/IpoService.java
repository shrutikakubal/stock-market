package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.IPODetails;

public interface IpoService {
	
	Optional<IPODetails> getIpoDetailsByCompanyName(String companyName);

	Iterable<IPODetails> getAllOrderByOpenDateTimeAsc();

	Optional<IPODetails> insertUpdateIpoDetails(IPODetails ipoDetails);


}
