package com.camping.constant;

import lombok.Getter;

@Getter
public enum Region {
	SEOUL("서울특별시"),
	BUSAN("부산광역시"),
	INCHEON("인천광역시"),
	DAEGU("대구광역시"),
	GWANGJU("광주광역시"),
	DAEJEON("대전광역시"),
	ULSAN("울산광역시"),
	SEJONG("세종특별자치시"),
	GYEONGGIDO("경기도"),
	GANGWONDO("강원도"),
	CHUNGCHEONGNAMDO("충청남도"),
	CHUNGCHEONGBUKDO("충청북도"),
	JEOLLANAMDO("전라남도"),
	JEOLLABUKDO("전라북도"),
	GYEONGSANGNAMDO("경상남도"),
	GYEONGSANGBUKDO("경상북도"),
	JEJU("제주도");
	
	private String krName;
	
	Region(String krName){
		this.krName = krName;
	}
	
}
