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
@Table(name="camping_img")
@Getter
@Setter
public class CampingImg extends BaseEntity{
	
	@Id
	@Column(name = "camping_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String camImgName;
	
	private String camOriImgName;
	
	private String camImgUrl;
	
	private String camRepImgYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="camping_id")
	private Camping camping;
	
	public void updateCampingImg(String camOriImgName, String camImgName, String camImgUrl) {
		this.camOriImgName = camOriImgName;
		this.camImgName = camImgName;
		this.camImgUrl = camImgUrl;
	}
	
	
}
