package com.camping.constant;

import lombok.Getter;

@Getter
public enum ReservationStatus {
	STANDBY("대기"),
	RESERVATION("예약"),
	CANCEL("취소");
	
	private String krName;
	
	ReservationStatus(String krName){
		this.krName = krName;
	}
}
