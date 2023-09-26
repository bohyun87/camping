package com.camping.constant;

import lombok.Getter;

@Getter
public enum Interest {
	CAMPING("노지캠핑"),
	GLAMPING("글램핑"),
	CARAVAN("카라반");
	
	private String krName;
	
	Interest(String krName){
		this.krName = krName;
	}
}
