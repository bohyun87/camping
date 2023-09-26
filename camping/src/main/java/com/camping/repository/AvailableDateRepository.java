package com.camping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.entity.AvailableDate;


public interface AvailableDateRepository  extends JpaRepository<AvailableDate, Long>{
	
	//List<AvailableDate> findByCampingSiteIdOrderByIdAsc(Long siteId);

}
