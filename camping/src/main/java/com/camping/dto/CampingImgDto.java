package com.camping.dto;

import org.modelmapper.ModelMapper;

import com.camping.entity.CampingImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampingImgDto {
	
	private Long id;
	
	private String camImgName;
	
	private String camOriImgName;
	
	private String camImgUrl;
	
	private String camRepImgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();

	public static CampingImgDto of(CampingImg campingImg) {
		return modelMapper.map(campingImg, CampingImgDto.class);
	}
	
	

}
