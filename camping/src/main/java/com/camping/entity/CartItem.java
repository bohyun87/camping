package com.camping.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cart_item")
@Getter
@Setter
@ToString
public class CartItem {

	@Id
	@Column(name="cart_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int rvCount;
	
	private LocalDateTime rvDate;
	
	private int RvPrice;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name="cart_id") 
	private Cart cart;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "camping_id")
	private Camping camping;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="site_id")
	private CampingSite campingSite;

	
}
