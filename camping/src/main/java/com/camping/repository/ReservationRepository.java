package com.camping.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camping.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	//현재 로그인한 회원 예약 데이터 조회
	@Query("select r from Reservation r where r.member.email = :email order by r.id desc")
	List<Reservation> findReservations(@Param("email") String email, Pageable pageable);
	
	//현재 로그인한 회원 예약 개수 조회
	@Query("select count(r) from Reservation r where r.member.email = :email")
	Long countReservation(@Param("email") String email);

}
