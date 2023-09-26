package com.camping.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto {
	
	@NotNull(message = "캠핑장 아이디는 필수 입력입니다.")
	private Long campingId;
	
	@NotNull(message = "캠핑장 사이트 아이디는 필수 입력입니다.")
	private Long siteId;
	
	/*
	@NotNull(message = "캠핑장 사이트 아이디는 필수 입력입니다.")
	private Long availableDateId; */
	
	@NotNull(message = "가격은 필수입력입니다.")
	private int price;
	
	@Min(value = 1, message = "최소 예약은 1일 입니다.")
	@Max(value = 5, message = "최대 연박 예약은 5일 입니다.")
	private int rvCount;
	
	@Min(value = 1, message = "최소인원은 1명.")
	@Max(value = 5, message = "최대인원은 5명이고, 2인 이상 시 추가요금 발생.")
	private int rvAdultCount;
	
	private int rvChildCount;
	
	@Max(value = 2, message = "최대 수용 가능 차량은 2대입니다. ")
	private int rvCarCount;
	
	private String rvOption;
	
	@NotBlank(message = "체크인 날짜를 선택하세요.")
	private String rvCheckinDate;
	
	@NotBlank(message = "체크아웃 날짜를 선택하세요.")
	private String rvCheckoutDate;
}
