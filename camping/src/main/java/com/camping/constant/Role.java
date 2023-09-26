package com.camping.constant;

import lombok.Getter;

@Getter
public enum Role {
	USER("일반사용자"),
	ADMIN("관리자");
	
	private String krName;
	
	Role(String krName){
		this.krName = krName;
	}
}
