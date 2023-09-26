package com.camping.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.entity.CampingSiteImg;

public interface SiteImgRepository extends JpaRepository<CampingSiteImg, Long> {
	
	List<CampingSiteImg> findByCampingSiteIdOrderByIdAsc(Long siteId);
	
	//대표이미지 찾는 메소드
	CampingSiteImg findByCampingSiteIdAndSiteRepImgYn(Long siteId, String siteRepImgYn);
	
}
