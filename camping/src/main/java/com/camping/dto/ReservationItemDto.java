package com.camping.dto;

import com.camping.entity.ReservationItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationItemDto {
	
	private String campingName;
	
	private String siteName;
	
	private String dateName;
	
	private int rvCount;
	
	private int price;
	
	private String camImgUrl;
	
	private String siteImgUrl;
	
	private String rvCheckinDate;
	
	private String rvCheckoutDate;
	
	private String rvOption;

	public ReservationItemDto(ReservationItem reservationItem, String camImgUrl, String siteImgUrl) {
		this.campingName = reservationItem.getCamping().getCampingName();
		this.siteName = reservationItem.getCampingSite().getSiteName();
		this.rvCount = reservationItem.getRvCount();
		this.price = reservationItem.getPrice();
		this.rvCheckinDate = reservationItem.getRvCheckinDate();
		this.rvCheckoutDate = reservationItem.getRvCheckoutDate();
		this.rvOption = reservationItem.getRvOption();
		this.camImgUrl = camImgUrl;
		this.siteImgUrl = siteImgUrl;
	}
}
