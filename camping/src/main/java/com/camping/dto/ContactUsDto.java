package com.camping.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.camping.constant.ContactStatus;
import com.camping.entity.ContactUs;
import com.camping.entity.ContactUsImg;
import com.camping.entity.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactUsDto {

	private Long id;
	
	@NotBlank(message="필수 입력 값입니다.")
	private String campingName;
	
	@NotBlank(message="필수 입력 값입니다.")
	private String price;
	
	@NotBlank(message="필수 입력 값입니다.")
	private String stock;
	
	@NotBlank(message="필수 입력 값입니다.")
	private String itemDetail;
	
	private ContactStatus contactStatus;
	
	private List<ContactUsImgDto> contactUsImgDtoList = new ArrayList<>();
	
	private List<Long> contactUsImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public ContactUs createContactUs(ContactUsDto contactUsDto) {
		ContactUs contactUs = new ContactUs();
		contactUs.setCampingName(contactUsDto.getCampingName());
		contactUs.setPrice(contactUsDto.getPrice());
		contactUs.setStock(contactUsDto.getStock());
		contactUs.setItemDetail(contactUsDto.getItemDetail());
		contactUs.setContactStatus(ContactStatus.WAIT);
		
		return contactUs;
	}
	
	public static ContactUsDto of(ContactUs contactUs) {
		return modelMapper.map(contactUs, ContactUsDto.class);
	}
	
}
