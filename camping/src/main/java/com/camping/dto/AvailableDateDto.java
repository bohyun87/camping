package com.camping.dto;

import org.modelmapper.ModelMapper;

import com.camping.entity.AvailableDate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AvailableDateDto {
	/*
	private Long id;
	
	private String rvCheckinDate;
	
	/*
	private int stockNumber;
	
	private int price;
	
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public AvailableDate createDate() {
		return modelMapper.map(this, AvailableDate.class);
	}
	
	public static AvailableDateDto of(AvailableDate availableDate) {
		return modelMapper.map(availableDate, AvailableDateDto.class);
	}
	*/
}
