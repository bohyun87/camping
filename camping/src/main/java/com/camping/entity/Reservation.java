package com.camping.entity;

import java.util.ArrayList;
import java.util.List;

import com.camping.constant.ReservationStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="reservation")
@Getter
@Setter
@ToString
public class Reservation extends BaseEntity{

	@Id
	@Column(name="reservation_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	@Enumerated(EnumType.STRING)
	private ReservationStatus reservationStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ReservationItem> reservationItems = new ArrayList<>();
	
	public void addReservationItem(ReservationItem reservationItem) {
		this.reservationItems.add(reservationItem);
		reservationItem.setReservation(this);
	}
	
	//order 객체 생성
	public static Reservation createReservation(Member member, List<ReservationItem> reservationItemList) {
		Reservation reservation = new Reservation();
		reservation.setMember(member);
		
		for(ReservationItem reservationItem : reservationItemList) {
			reservation.addReservationItem(reservationItem);
			
		}
		reservation.setReservationStatus(ReservationStatus.STANDBY);
		
		return reservation;
	}
	
	//총 주문 금액
	public int getTotalPrice() {
		int totalPrice = 0;
		for(ReservationItem reservationItem : reservationItems) {
			totalPrice += reservationItem.getTotalPrice();
		}
		
		return totalPrice;
	}
	
	//주문취소
	public void cancelReservation() {
		this.reservationStatus = ReservationStatus.CANCEL;
		
		for(ReservationItem reservationItem : reservationItems) {
			reservationItem.cancel();
		}
	}
}
