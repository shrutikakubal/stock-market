package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.IPODetails;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.IPODtls;
import com.example.demo.service.IpoService;

@RestController
@RequestMapping("/ipo")
@CrossOrigin
public class IpoController {

	@Autowired
	IpoService ipoService;

	@RequestMapping(value = "/name/{companyName}", method = RequestMethod.GET)
	public ResponseEntity<IPODtls> getIpoByName(@PathVariable("companyName") String companyName) {
		Optional<IPODetails> ipoDetails = ipoService.getIpoDetailsByCompanyName(companyName);
		IPODtls ipoDtls = convertToDto(ipoDetails.orElseThrow(() -> new ResourceNotFoundException("No IPO For this company")));
		return ResponseEntity.status(HttpStatus.FOUND).body(ipoDtls);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<IPODtls>> getAllIpo() {
		Iterable<IPODetails> ipoList = ipoService.getAllOrderByOpenDateTimeAsc();
		List<IPODtls> ipoDtlsList = new ArrayList<IPODtls>();
		for (IPODetails i : ipoList) {
			ipoDtlsList.add(convertToDto(i));
		}
		return ResponseEntity.status(HttpStatus.OK).body(ipoDtlsList);
	}

	@RequestMapping(value = "/admin/new", method = RequestMethod.POST)
	public ResponseEntity<IPODtls> addNewIpo(@RequestBody IPODtls ipoDtls) {
		IPODetails ipoDetails = (new ModelMapper()).map(ipoDtls, IPODetails.class);
		Optional<IPODetails> ipo = ipoService.insertUpdateIpoDetails(ipoDetails);
		ipoDtls = convertToDto(ipo.get());
		return ResponseEntity.status(HttpStatus.CREATED).body(ipoDtls);
	}

	@RequestMapping(value = "/admin/update/{companyName}", method = RequestMethod.PUT)
	public ResponseEntity<IPODtls> updateIpo(@PathVariable("companyName") String companyName,
			@RequestBody IPODtls ipoDtls) {
		IPODetails ipoDetails = ipoService.getIpoDetailsByCompanyName(companyName)
				.orElseThrow(() -> new ResourceNotFoundException("No IPO for this company"));
		new ModelMapper().map(ipoDtls, ipoDetails);
		ipoDtls = convertToDto(ipoService.insertUpdateIpoDetails(ipoDetails).get());
		return ResponseEntity.status(HttpStatus.OK).body(ipoDtls);
	}

	private IPODtls convertToDto(IPODetails ipoDetails) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		IPODtls ipoDtls = modelMapper.map(ipoDetails, IPODtls.class);
		return ipoDtls;

	}
}
