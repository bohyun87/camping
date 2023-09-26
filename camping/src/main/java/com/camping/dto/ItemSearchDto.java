package com.camping.dto;

import com.camping.constant.CampGround;
import com.camping.constant.CampingType;
import com.camping.constant.Region;
import com.camping.constant.SiteSellStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {
	private SiteSellStatus searchSiteSellStatus;
	private CampGround searchCampGround;
	private CampingType searchCampingType;
	private Region searchRegion;
	private String searchBy;	
	private String searchQuery = "";
}
