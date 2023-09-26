package com.camping.entity;

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

@Entity
@Table(name="site_img")
@Getter
@Setter
public class CampingSiteImg extends BaseEntity{
	
	@Id
	@Column(name = "site_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String siteImgName;
	
	private String siteOriImgName;
	
	private String siteImgUrl;
	
	private String siteRepImgYn;
	
	@ManyToOne(fetch =FetchType.LAZY )
	@JoinColumn(name = "site_id")
	private CampingSite campingSite;
	
	public void updateSiteImg(String siteOriImgName, String siteImgName, String siteImgUrl) {
		this.siteOriImgName = siteOriImgName;
		this.siteImgName = siteImgName;
		this.siteImgUrl = siteImgUrl;
	}
	
}
