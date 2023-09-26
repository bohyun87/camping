package com.camping.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.camping.constant.ReservationStatus;
import com.camping.entity.Reservation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationHistDto {
	
	private Long reservationId;
	private ReservationStatus reservationStatus;
	private String rvCheckinDate;
	private String rvCheckoutDate;
	private List<ReservationItemDto> reservationItemDtoList = new ArrayList<>();
	
	//엔티티 -> Dto 로 변환
	public ReservationHistDto(Reservation reservation) {
		this.reservationId = reservation.getId();
		this.reservationStatus = reservation.getReservationStatus();
	}
	
	//ReservationItemDto 객체를 예약상품리스트에 추가
	public void addReservationItemDto(ReservationItemDto reservationItemDto) {
		this.reservationItemDtoList.add(reservationItemDto);
	}
}
