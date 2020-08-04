package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IpoDao;
import com.example.demo.entity.IPODetails;

@Service
@EnableTransactionManagement
public class IpoServiceImpl implements IpoService {

	@Autowired
	private IpoDao ipoDao;

	@Override
	@Transactional
	public Optional<IPODetails> getIpoDetailsByCompanyName(String companyName) {

		return ipoDao.findByCompanyName(companyName);
	}

	@Override
	@Transactional
	public Iterable<IPODetails> getAllOrderByOpenDateTimeAsc() {

		return ipoDao.findAllByOrderByOpenDateTimeAsc();
	}

	@Override
	@Transactional
	public Optional<IPODetails> insertUpdateIpoDetails(IPODetails ipoDetails) {
		ipoDao.save(ipoDetails);
		return Optional.of(ipoDetails);
	}


}
