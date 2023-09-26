package com.camping.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.camping.constant.CampGround;
import com.camping.constant.CampingType;
import com.camping.constant.Region;
import com.camping.constant.SiteSellStatus;
import com.camping.entity.Camping;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampingFormDto {
	
	private Long id;
	
	@NotBlank(message = "캠핑장명은 필수 입력 값입니다.")
	private String campingName;
	
	@NotBlank(message = "캠핑장 부제목은 필수 입력 값입니다.")
	private String campingSubName;
	
	private Region region;
	
	@NotBlank(message = "캠핑장 주소는 필수 입력 값입니다.")
	private String address;
	
	@NotBlank(message = "캠핑장 연락처는 필수 입력 값입니다.")
	private String tel;
	
	@NotNull(message = "캠핑장유형은 필수 선택입니다.")
	private CampingType campingType;
	
	@NotNull(message = "캠핑장환경은 필수 선택입니다.")
	private CampGround campGround;
	
	@NotBlank(message = "캠핑장 운영기간은 필수 입력 값입니다.")
	private String operation;
	
	@NotBlank(message = "주변이용시설은 필수 입력 값입니다.")
	private String surrounding;
	
	@NotBlank(message = "캠핑장 설명은 필수 입력 값입니다.")
	private String campingDetail;
	
	@NotNull(message = "제휴상태는 필수 선택입니다.")
	private SiteSellStatus siteSellStatus;
	
	private List<SiteDto> siteDtoList = new ArrayList<>();
	
	private List<CampingImgDto> campingImgDtoList = new ArrayList<>();
	
	private List<Long> campingImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Camping createCamping() {
		return modelMapper.map(this, Camping.class);
	} 
	
	public static CampingFormDto of(Camping camping) {
		return modelMapper.map(camping, CampingFormDto.class);
	}
	

}
