package com.camping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.camping.dto.ReservationDto;
import com.camping.dto.ReservationHistDto;
import com.camping.dto.ReservationItemDto;
import com.camping.entity.AvailableDate;
import com.camping.entity.Camping;
import com.camping.entity.CampingImg;
import com.camping.entity.CampingSite;
import com.camping.entity.CampingSiteImg;
import com.camping.entity.Member;
import com.camping.entity.Reservation;
import com.camping.entity.ReservationItem;
import com.camping.repository.CampingImgRepository;
import com.camping.repository.CampingRepository;
import com.camping.repository.MemberRepository;
import com.camping.repository.ReservationRepository;
import com.camping.repository.SiteImgRepository;
import com.camping.repository.SiteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
	
	private final CampingRepository campingRepository;
	private final CampingImgRepository campingImgRepository;
	
	private final SiteRepository siteRepository;
	private final SiteImgRepository siteImgRepository;
	
	private final MemberRepository memberRepository;
	private final ReservationRepository reservationRepository;

	
	//예약하기
	public Long reservation(ReservationDto reservationDto, String email) {
		
		//예약상품 조회
		Camping camping = campingRepository.findById(reservationDto.getCampingId()).orElseThrow(EntityNotFoundException::new);
		CampingSite site = siteRepository.findById(reservationDto.getSiteId()).orElseThrow(EntityNotFoundException::new);
		
		//AvailableDate availableDate = avilableDateRepository.findById(reservationDto.getAvailableDateId()).orElseThrow(EntityNotFoundException::new);
		
		//현재 로그인한 회원의 이메일로 회원정보 조회
		Member member = memberRepository.findByEmail(email);
		
		//예약 상품 엔티티 생성
		List<ReservationItem> reservationItemList = new ArrayList<>();
		ReservationItem reservationItem = ReservationItem.createReservationItem(camping, site, reservationDto.getPrice(), reservationDto.getRvCount(), reservationDto.getRvCheckinDate(),
				reservationDto.getRvCheckoutDate(), reservationDto.getRvAdultCount(), reservationDto.getRvChildCount(), reservationDto.getRvCarCount(), 
				reservationDto.getRvOption());
		reservationItemList.add(reservationItem);
		
		Reservation reservation = Reservation.createReservation(member, reservationItemList);
		reservationRepository.save(reservation);
		
		return reservation.getId();		
	}
	
	//예약목록 메소드
	public Page<ReservationHistDto> getReservationList(String email, Pageable pageable){
		
		//유저아이디, 페이징으로 예약목록 조회
		List<Reservation> reservations = reservationRepository.findReservations(email, pageable);
		
		//유저의 예약 총 개수 구하기
		Long totalCount = reservationRepository.countReservation(email);
		
		//예약 리스트를 마이페이지로 전달할 Dto 생성
		List<ReservationHistDto> reservationHistDtoList = new ArrayList<>();
		
		//예약상품 가져오기
		for(Reservation reservation : reservations) {
			ReservationHistDto reservationHistDto = new ReservationHistDto(reservation);
			List<ReservationItem> reservationItems = reservation.getReservationItems();
			
			for(ReservationItem reservationItem : reservationItems) {
				CampingImg campingImg = campingImgRepository.findByCampingIdAndCamRepImgYn(reservationItem.getCamping().getId(), "Y");
				CampingSiteImg campingSiteImg = siteImgRepository.findByCampingSiteIdAndSiteRepImgYn(reservationItem.getCampingSite().getId(), "Y");

				ReservationItemDto reservationItemDto = new ReservationItemDto(reservationItem, campingImg.getCamImgUrl(), campingSiteImg.getSiteImgUrl());
				
				reservationHistDto.addReservationItemDto(reservationItemDto);			
			}
			
			reservationHistDtoList.add(reservationHistDto);			
		}
		
		return new PageImpl<>(reservationHistDtoList, pageable, totalCount);
	}
	
	//본인확인(로그인 회원 = 예약한 회원)
	public boolean validateReservation(Long reservationId, String email) {
		Member curMember = memberRepository.findByEmail(email);
		Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
		
		Member saveMember = reservation.getMember();
		
		if(!StringUtils.equals(curMember.getEmail(), saveMember.getEmail())) {
			return false;
		}
		
		return true;
		
	}
	
	
	//주문취소
	public void cancelReservation(Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
		
		reservation.cancelReservation();
	}
	
	//주문삭제
	public void deleteReservation(Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
		
		reservationRepository.delete(reservation);
	}
	
}
