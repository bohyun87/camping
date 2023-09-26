package com.camping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camping.dto.CampingFormDto;
import com.camping.dto.CampingListDto;
import com.camping.dto.ItemSearchDto;
import com.camping.dto.MainCampingDto;
import com.camping.entity.Camping;

public interface CampingRepositoryCustom {

	//campingMng.html 페이징
	Page<CampingListDto> getAdminMngPage(ItemSearchDto itemSearchDto, Pageable pageable);
	
	//camping.html 페이징
	Page<CampingListDto> getCampingPage(ItemSearchDto itemSearchDto, Pageable pageable);
	
	//mail.html 페이징
	Page<MainCampingDto> getMainCampingDto(ItemSearchDto itemSearchDto, Pageable pageable);
	
}
