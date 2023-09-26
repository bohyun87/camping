package com.camping.constant;

import lombok.Getter;

@Getter
public enum CampingType {
	GENERAL("일반캠핑장"),
	CAR("자동차캠핑장"),
	GLAMPING("글램핑"),
	CARAVAN("카라반");
	
	private String krName;
	
	CampingType(String krName){
		this.krName = krName;
	}
}
