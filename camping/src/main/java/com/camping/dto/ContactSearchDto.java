package com.camping.dto;

import com.camping.constant.ContactStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactSearchDto {

	private ContactStatus searchContactStatus;
	private String searchBy;	
	private String searchQuery = "";
	
}
