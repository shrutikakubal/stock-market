package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.IPODetails;

@Repository
public interface IpoDao extends JpaRepository<IPODetails, Integer> {

	Optional<IPODetails> findByCompanyName(String companyName);

	Iterable<IPODetails> findAllByOrderByOpenDateTimeAsc();
}
