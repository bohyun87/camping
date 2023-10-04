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
@Table(name="contact_us_img")
@Getter
@Setter
public class ContactUsImg {

	@Id
	@Column(name="contact_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String contactImgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private ContactUs contactUs;
	
	public void updateContactUsImg(String oriImgName, String contactImgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.contactImgName = contactImgName;
		this.imgUrl = imgUrl;
	}
	
}
