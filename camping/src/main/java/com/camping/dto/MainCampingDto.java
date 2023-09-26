package com.camping.dto;

import com.camping.constant.Region;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainCampingDto {
	
	private Long id;
	private String campingName;
	private String campingSubName;
	private Region region;
	private String address;
	private String camImgUrl;
	
	@QueryProjection
	public MainCampingDto(Long id, String campingName, String campingSubName, Region region, String address, String camImgUrl) {
		this.id = id;
		this.campingName = campingName;
		this.campingSubName = campingSubName;
		this.region = region;
		this.address = address;
		this.camImgUrl = camImgUrl;
	}
	
}
