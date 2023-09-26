package com.camping.entity;

import com.camping.exception.OutOfStockException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="available_date")
@Getter
@Setter
@ToString
public class AvailableDate {
	
	@Id
	@Column(name="available_date_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/*
	private String rvCheckinDate;
	
	private int stockNumber;
	/*
	private int price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="site_id")
	private CampingSite campingSite;
	
	
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
	*/


}
