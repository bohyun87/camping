package com.camping.dto;

import org.modelmapper.ModelMapper;

import com.camping.entity.CampingSiteImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteImgDto {
	
	private Long id;
	
	private String siteImgName;
	
	private String siteOriImgName;
	
	private String siteImgUrl;
	
	private String siteRepImgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static SiteImgDto of(CampingSiteImg campingSiteImg) {
		return modelMapper.map(campingSiteImg, SiteImgDto.class);
	}
}
