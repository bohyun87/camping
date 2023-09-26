package com.camping.dto;

import java.util.ArrayList;
import java.util.List;

import com.camping.constant.CampGround;
import com.camping.constant.CampingType;
import com.camping.constant.Region;
import com.camping.constant.SiteSellStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampingListDto{
	
	private Long id;
	
	private String campingName;
	
	private String campingSubName;
	
	private Region region;
	
	private String address;
	
	private String tel;
	
	private String campingDetail;
	
	private String camImgUrl;
	
	private CampGround campGround;
	
	private CampingType campingType;
	
	private SiteSellStatus siteSellStatus;
	
	private String operation;
	
	private String surrounding;
	
	private List<SiteDto> siteDtoList = new ArrayList<>();
	
	//관리페이지 생성자
	@QueryProjection
	public CampingListDto(Long id, String campingName, Region region, CampGround campGround, CampingType campingType, SiteSellStatus siteSellStatus, String camImgUrl){
		this.id = id;
		this.campingName = campingName;
		this.region = region;
		this.camImgUrl = camImgUrl;
		this.campGround = campGround;
		this.campingType = campingType;
		this.siteSellStatus = siteSellStatus;
	}
	
	//캠핑all 페이지 생성자
	@QueryProjection
	public CampingListDto(Long id, String campingName, String campingSubName, Region region, String address, String tel, String campingDetail, String camImgUrl) {
		this.id = id;
		this.campingName = campingName;
		this.campingSubName = campingSubName;
		this.region = region;
		this.address = address;
		this.campingDetail = campingDetail;
		this.tel = tel;
		this.camImgUrl = camImgUrl;	
	}
	
	//캠핑&사이트 페이지 생성자
	@QueryProjection
	public CampingListDto(Long id, String campingName, String campingSubName, String address, String tel, CampingType campingType, CampGround campGround, String operation, String surrounding, String camImgUrl) {
		this.id = id;
		this.campingName = campingName;
		this.campingSubName = campingSubName;
		this.address = address;
		this.tel = tel;
		this.campingType = campingType;
		this.campGround = campGround;
		this.operation = operation;
		this.surrounding = surrounding;
		this.camImgUrl = camImgUrl;
	}
	
	@QueryProjection
	public CampingListDto(Long id, String campingName, String campingSubName, Region region, String address, String tel, String campingDetail, String camImgUrl,
			CampGround campGround, CampingType campingType, SiteSellStatus siteSellStatus) {
		this.id = id;
		this.campingName = campingName;
		this.campingSubName = campingSubName;
		this.region = region;
		this.address = address;
		this.tel = tel;
		this.campingDetail = campingDetail;
		this.camImgUrl = camImgUrl;
		this.campGround = campGround;
		this.campingType = campingType;
		this.siteSellStatus = siteSellStatus;		
	}
}
