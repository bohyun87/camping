package com.camping.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camping.dto.ReservationDto;
import com.camping.dto.ReservationHistDto;
import com.camping.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservationController {
	
	private final ReservationService reservationService;
	
	//예약하기
	@PostMapping(value = "/reservation")
	public @ResponseBody ResponseEntity reservationList(@RequestBody @Valid ReservationDto reservationDto, 
					BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage());
			}
			
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		String email = principal.getName(); //email 가져오기
		Long reservationId;
		
		try {
			reservationId = reservationService.reservation(reservationDto, email);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Long>(reservationId, HttpStatus.OK);
			
	}
	
	//예약내역보여주기
	@GetMapping(value = {"/reservations", "/reservations/{page}"})
	public String reservationHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
		//1페이지 4건 예약 출력
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
		
		//서비스호출
		Page<ReservationHistDto> reservationHistDtoList = reservationService.getReservationList(principal.getName(), pageable);
		
		//서비스에서 가져온 값 model에 담기
		model.addAttribute("reservations", reservationHistDtoList);
		model.addAttribute("maxPage", 5);

		return "reservation/reservationHist";
	}
	
	//예약취소
	@PostMapping(value = "/reservations/{reservationId}/cancel")
	public @ResponseBody ResponseEntity cancelReservation(@PathVariable("reservationId") Long reservationId, Principal principal) {
		
		//취소권한 조회
		if(!reservationService.validateReservation(reservationId, principal.getName())) {
			return new ResponseEntity<String>("예약 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		//취소 실행
		reservationService.cancelReservation(reservationId);
		
		return new ResponseEntity<Long>(reservationId, HttpStatus.OK);
	}
	
	//예약내역 삭제
	@DeleteMapping(value = "/reservations/{reservationId}/delete")	
	public @ResponseBody ResponseEntity deleteReservation(@PathVariable("reservationId") Long reservationId, Principal principal) {
		
		//본인확인
		if(!reservationService.validateReservation(reservationId, principal.getName())) {
			return new ResponseEntity<String>("예약 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		//예약삭제
		reservationService.deleteReservation(reservationId);
		
		return new ResponseEntity<Long>(reservationId, HttpStatus.OK);
	}
		
}
