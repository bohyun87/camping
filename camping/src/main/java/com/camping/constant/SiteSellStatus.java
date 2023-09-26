package com.camping.constant;


import lombok.Getter;

@Getter
public enum SiteSellStatus {
	SELL("판매중"), 
	SOLD_OUT("판매종료");
	
	private String krName;
	
	SiteSellStatus(String krName){
		this.krName = krName;
	}
	

}
