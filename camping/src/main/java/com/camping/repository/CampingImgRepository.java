package com.camping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.entity.CampingImg;

public interface CampingImgRepository extends JpaRepository<CampingImg, Long> {
	
	List<CampingImg> findByCampingIdOrderByIdAsc(Long campingId);
	
	//대표이미지 찾는 메소드
	CampingImg findByCampingIdAndCamRepImgYn(Long campingId, String camRepimgYn);
	

}
