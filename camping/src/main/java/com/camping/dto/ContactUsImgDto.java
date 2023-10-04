package com.camping.dto;

import org.modelmapper.ModelMapper;

import com.camping.entity.ContactUsImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactUsImgDto {

	private Long id;
	
	private String contactImgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ContactUsImgDto of(ContactUsImg contactUsImg) {
		return modelMapper.map(contactUsImg, ContactUsImgDto.class);
	}
	
}
