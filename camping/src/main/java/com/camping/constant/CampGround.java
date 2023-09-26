package com.camping.constant;

import lombok.Getter;

@Getter
public enum CampGround {
	MOUNTAIN("산"),
	FOREST("숲"),
	BEACH("해변"),
	RIVER("강"),
	ISLAND("섬"),
	VALLERY("계곡"),
	LAKE("호수"),
	DOWNTOWN("도심");
	
	private String krName;
	
	CampGround(String krName){
		this.krName = krName;
	}
}
