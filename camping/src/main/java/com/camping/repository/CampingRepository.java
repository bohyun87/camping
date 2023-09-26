package com.camping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.dto.CampingListDto;
import com.camping.entity.Camping;

public interface CampingRepository extends JpaRepository<Camping, Long>, CampingRepositoryCustom {

	
	List<CampingListDto> findByCampingName(String campingName);
	
	
}
