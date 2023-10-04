package com.camping.entity;

import com.camping.constant.SiteSellStatus;
import com.camping.dto.SiteDto;
import com.camping.exception.OutOfStockException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "site")
@Getter
@Setter
@ToString
public class CampingSite extends BaseEntity{

	@Id
	@Column(name="site_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String siteName;
		
	
	@Column(nullable = false)
	private String peakSeason;
	
	@Column(nullable = false)
	private String offSeason;
	
	@Column(nullable = false)
	private String person;
	

	@Column(nullable = false, name="price")
	private int price;

	@Column(nullable = false)
	private int stockNumber;
		
	@Column(nullable = false)
	private String sevenDaysAgo;

	@Column(nullable = false)
	private String fiveDaysAgo;
	
	@Column(nullable = false)
	private String threeDaysAgo;
	
	@Column(nullable = false)
	private String twoDaysAgo;
	
	@Column(nullable = false)
	private String oneDaysAgo; 
	
	@Column(nullable = false)
	private String information;
	
	@Column(nullable = false)
	private String roomType;
	
	@Column(nullable = false)
	private String roomView;
	
	@Column(nullable = false)
	private String requiredItem;
	
	@Column(nullable = false)
	private String checkInTime;
	
	@Column(nullable = false)
	private String checkOutTime;
	
	@Column(nullable = false)
	private String mannerTime;	
	
	@Enumerated(EnumType.STRING)
	private SiteSellStatus siteSellStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "camping_id")
	private Camping camping;	
	
	
	//site 엔티티 수정
	public void updateSite(SiteDto siteDto) {
		this.siteName = siteDto.getSiteName();
		this.peakSeason = siteDto.getPeakSeason();
		this.offSeason = siteDto.getOffSeason();
		this.person = siteDto.getPerson();
		this.price = siteDto.getPrice();
		this.stockNumber = siteDto.getStockNumber();
		this.sevenDaysAgo = siteDto.getSevenDaysAgo();
		this.fiveDaysAgo = siteDto.getFiveDaysAgo();
		this.threeDaysAgo = siteDto.getThreeDaysAgo();
		this.twoDaysAgo = siteDto.getTwoDaysAgo();
		this.oneDaysAgo = siteDto.getOneDaysAgo();
		this.information = siteDto.getInformation();
		this.roomType = siteDto.getRoomType();
		this.roomView = siteDto.getRoomView();
		this.requiredItem = siteDto.getRequiredItem();
		this.checkInTime = siteDto.getCheckInTime();
		this.checkOutTime = siteDto.getCheckOutTime();
		this.mannerTime = siteDto.getMannerTime();
		this.siteSellStatus = siteDto.getSiteSellStatus();
	}
	
	
	//재고감소
	public void removeStock(int stockNumber) {
		int restStock = this.stockNumber - stockNumber;
		
		if(restStock < 0) {
			throw new OutOfStockException("상품의 재고가 부족합니다." + "현재 재고수량: " + this.stockNumber );
		}
		
		this.stockNumber = restStock;
		
	}
	
	//재고증가
	public void addStock(int stockNumber) {
		this.stockNumber += stockNumber;
	}
	
	
}
