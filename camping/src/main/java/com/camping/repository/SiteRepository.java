package com.camping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.entity.CampingSite;

public interface SiteRepository extends JpaRepository<CampingSite, Long> {
	List<CampingSite> findBySiteName(String siteName);
	
	List<CampingSite> findByCampingId(Long campingId);
	
}
