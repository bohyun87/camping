package com.camping.entity;

import com.camping.constant.ContactStatus;
import com.camping.dto.ContactUsDto;

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
@Table(name="contact_us")
@Getter
@Setter
@ToString
public class ContactUs extends BaseEntity{

	@Id
	@Column(name="contact_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = true)
	private String campingName;
	
	@Column(nullable = true)
	private String price;
	
	@Column(nullable = true)
	private String stock;
	
	@Column(nullable = true)
	private String itemDetail;
	
	private ContactStatus contactStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	public void updateContactUs(ContactUsDto contactUsDto) {
		this.campingName = contactUsDto.getCampingName();
		this.price = contactUsDto.getPrice();
		this.stock = contactUsDto.getStock();
		this.itemDetail = contactUsDto.getItemDetail();
		this.contactStatus = contactUsDto.getContactStatus();
	}
	
}
