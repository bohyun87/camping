package com.camping.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.camping.constant.SiteSellStatus;
import com.camping.entity.Camping;
import com.camping.entity.CampingSite;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteDto {
	
	private Long Id;
	
	@NotBlank(message = "사이트명은 필수 입력 값입니다.")
	private String siteName;
	
	@NotNull(message = "가격은 필수 입력입니다.")
	private int price;
	
	@NotNull(message = "재고는 필수 입력입니다.")
	private int stockNumber;

	@NotBlank(message = "성수기 가격은 필수 입력 값입니다.")
	private String peakSeason;

	@NotBlank(message = "비수기 가격은 필수 입력 값입니다.")
	private String offSeason;
	
	@NotBlank(message = "기준인원은 필수 입력 값입니다.")
	private String person;
	
	@NotBlank(message = "7일 전 환불은 필수 입력 값입니다.")
	private String sevenDaysAgo;
	
	private String fiveDaysAgo;
	
	private String threeDaysAgo;
	
	private String twoDaysAgo;
	
	@NotBlank(message = "1일 전 환불은 필수 입력 값입니다.")
	private String oneDaysAgo;
	
	@NotBlank(message = "정보는 필수 입력 값입니다.")
	private String information;
	
	@NotBlank(message = "객실유형은 필수 입력 값입니다.")
	private String roomType;
	
	@NotBlank(message = "객실전망은 필수 입력 값입니다.")
	private String roomView;
	
	@NotBlank(message = "구비품목은 필수 입력 값입니다.")
	private String requiredItem;
	
	@NotBlank(message = "입실시간은 필수 입력 값입니다.")
	private String checkInTime;
	
	@NotBlank(message = "퇴실시간은 필수 입력 값입니다.")
	private String checkOutTime;
	
	@NotBlank(message = "매너타임은 필수 입력 값입니다.")
	private String mannerTime;
	
	private SiteSellStatus siteSellStatus;
	
	@NotNull(message = "캠핑장은 필수 선택입니다.")
	private Camping camping;
	
	private List<SiteImgDto> siteImgDtoList = new ArrayList<>();
	
	//private List<AvailableDateDto> availableDateDtoList = new ArrayList<>();
	
	private List<Long> siteImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public CampingSite createSite() {
		return modelMapper.map(this, CampingSite.class);
	}
	
	public static SiteDto of(CampingSite campingSite) {
		return modelMapper.map(campingSite, SiteDto.class);
	}
	
}
