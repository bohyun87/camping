package com.camping.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="reservation_item")
@Getter
@Setter
@ToString
public class ReservationItem {
	
	@Id
	@Column(name="rv_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int price;
	
	private int rvCount;
	
	private String rvCheckinDate;
	
	private String rvCheckoutDate;
	
	private int rvAdultCount;
	
	private int rvChildCount;
	
	private int rvCarCount;
	
	private String rvOption;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="reservation_id")
	private Reservation reservation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "camping_id")
	private Camping camping;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="site_id")
	private CampingSite campingSite;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "available_date_id")
	private AvailableDate availableDate;
	
	public static ReservationItem createReservationItem(Camping camping, CampingSite site, int price, int rvCount, String rvCheckinDate, String rvCheckoutDate, 
					int rvAdultCount, int rvChildCount, int rvCarCount, String rvOption) {
		
		ReservationItem reservationItem = new ReservationItem();
		reservationItem.setCamping(camping);
		reservationItem.setCampingSite(site);
		reservationItem.setPrice(price);
		reservationItem.setRvCount(rvCount);
		reservationItem.setRvCheckinDate(rvCheckinDate);
		reservationItem.setRvCheckoutDate(rvCheckoutDate);
		reservationItem.setRvAdultCount(rvAdultCount);
		reservationItem.setRvChildCount(rvChildCount);
		reservationItem.setRvCarCount(rvCarCount);
		reservationItem.setRvOption(rvOption);
		
		site.removeStock(rvCount);
		
		return reservationItem;
	}
	
	
	//총가격
	public int getTotalPrice() {
		return price * rvCount;
	}
	
	
	//주문취소
	public void cancel() {
		this.getCampingSite().addStock(rvCount);
	}
	
}
