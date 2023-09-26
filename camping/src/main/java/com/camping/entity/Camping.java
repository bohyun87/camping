package com.camping.entity;

import com.camping.constant.CampGround;
import com.camping.constant.CampingType;
import com.camping.constant.Region;
import com.camping.constant.SiteSellStatus;
import com.camping.dto.CampingFormDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="camping")
@Getter
@Setter
@ToString
public class Camping extends BaseEntity{

	@Id
	@Column(name="camping_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String campingName;
	
	@Column(nullable = false, length = 200)
	private String campingSubName;
	
	@Enumerated(EnumType.STRING)
	private Region region;
	
	@Column(nullable = false, length = 50)
	private String address;
	
	@Column(nullable = false, length = 50)
	private String tel;
	
	@Enumerated(EnumType.STRING)
	private CampingType campingType;
	
	@Enumerated(EnumType.STRING)
	private CampGround campGround;
	
	@Column(nullable = false, length = 50)
	private String operation;
	
	@Column(nullable = false, length = 100)
	private String surrounding;
	
	@Lob
	@Column(nullable = false, columnDefinition = "longtext")
	private String campingDetail;
	
	@Enumerated(EnumType.STRING)
	private SiteSellStatus siteSellStatus;

	
	public void updateCamping(CampingFormDto campingFormDto) {
		this.campingName = campingFormDto.getCampingName();
		this.campingSubName = campingFormDto.getCampingSubName();
		this.region = campingFormDto.getRegion();
		this.address = campingFormDto.getAddress();
		this.tel = campingFormDto.getTel();
		this.campingType = campingFormDto.getCampingType();
		this.campGround = campingFormDto.getCampGround();
		this.operation = campingFormDto.getOperation();
		this.surrounding = campingFormDto.getSurrounding();
		this.campingDetail = campingFormDto.getCampingDetail();
		this.siteSellStatus = campingFormDto.getSiteSellStatus();		
	}
	
	
}
