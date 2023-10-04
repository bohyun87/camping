package com.camping.constant;

import lombok.Getter;

@Getter
public enum ContactStatus {
	WAIT("답변 대기중"),
	COMPLETE("답변완료");
	
	private String krName;
	
	ContactStatus(String krName){
		this.krName = krName;
	}
	
}
