package com.camping.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.camping.dto.AvailableDateDto;
import com.camping.dto.CampingFormDto;
import com.camping.dto.CampingListDto;
import com.camping.dto.ItemSearchDto;
import com.camping.dto.SiteDto;
import com.camping.entity.Camping;
import com.camping.service.CampingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

	private final CampingService campingService;
	
	//전체 캠핑장 리스트
	@GetMapping(value = {"/camping/all", "/camping/all/{page}"})
	public String camping(Model model, ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page ) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
		Page<CampingListDto> campings = campingService.getCampingPage(itemSearchDto, pageable);
		
		model.addAttribute("campings", campings);
		model.addAttribute("itemSearchDto", itemSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "item/camping";
	} 
	
	//캠핑장 상세페이지
	@GetMapping(value = "/camping/{campingId}")
	public String campingDetail(Model model, @PathVariable("campingId") Long campingId) {
		
		CampingFormDto campingFormDto = campingService.getCampingDtl(campingId);
		
		
		model.addAttribute("camping", campingFormDto);
		model.addAttribute("sites", campingFormDto.getSiteDtoList());
			
		
		return "item/campingList";
	} 
		
	//사이트예약 페이지
	@GetMapping(value = "/camping/reservation/camping={campingId}/site={siteId}")
	public String campingReservation(Model model, @PathVariable("campingId") Long campingId, @PathVariable("siteId") Long siteId) {
		
		CampingFormDto campingFormDto = campingService.getCampingDtl(campingId);
		model.addAttribute("camping", campingFormDto);

		SiteDto siteDto = campingService.getSiteDtl(siteId);
		model.addAttribute("site", siteDto);
		
		//model.addAttribute("availableDates", siteDto.getAvailableDateDtoList());
		
		return "item/campingReservation";
	}
	

	//캠핑장 등록 페이지
	@GetMapping(value = "/admin/camping/register")
	public String campingReg(Model model) {
		model.addAttribute("campingFormDto", new CampingFormDto());
		return "item/campingReg";
	} 
	
	//캠핑장 등록 메소드
	@PostMapping(value = "/admin/camping/register")
	public String campingNew(@Valid CampingFormDto campingFormDto, BindingResult bindingResult, Model model,
			@RequestParam("campingImgFile") List<MultipartFile> campingImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "item/campingReg";
		}	
		
		if(campingImgFileList.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력입니다.");
			return "item/campingReg";			
		}
		
		try {
			campingService.saveCamping(campingFormDto, campingImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품등록 중 에러가 발생했습니다.");
			return "item/campingReg";
		}
		return "redirect:/";
	}
	
	//캠핑사이트 등록페이지
	@GetMapping(value = "/admin/site/register")
	public String siteReg(Model model) {
		List<Camping> listCamping = campingService.listAll();
		
		model.addAttribute("siteDto", new SiteDto());
		model.addAttribute("listCamping", listCamping);
		return "item/siteReg";
	} 
	
	//사이트 등록 메소드
	@PostMapping(value = "/admin/site/register")
	public String siteNew(@Valid SiteDto siteDto, BindingResult bindingResult, Model model,
			@RequestParam("siteImgFile") List<MultipartFile> siteImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "item/siteReg";
		}	
		
		if(siteImgFileList.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력입니다.");
			return "item/siteReg";			
		}
		
		try {
			campingService.saveSite(siteDto, siteImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품등록 중 에러가 발생했습니다.");
			return "item/siteReg";
		}
		return "redirect:/";
	}
	
	//상품관리 페이지
	@GetMapping(value = {"/admin/campings", "/admin/campings/{page}"})
	public String itemMng(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		
		try {
			Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
			Page<CampingListDto> campings = campingService.getAdminMngPage(itemSearchDto, pageable);
			
			model.addAttribute("campings", campings);
			model.addAttribute("itemSearchDto", itemSearchDto);
			model.addAttribute("maxPage", 5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return "item/itemMng";
	} 
	
	//캠핑장 수정페이지 보여주기
	@GetMapping(value = "/admin/camping/{campingId}")
	public String campingModify(@PathVariable("campingId") Long campingId, Model model) {
		
		try {
			CampingFormDto campingFormDto = campingService.getCampingDtl(campingId);
			model.addAttribute("campingFormDto", campingFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품정보를 가져올때 에러가 발생했습니다.");
			
			model.addAttribute("campingFormDto", new CampingFormDto());
			
			return "item/campingReg";
		}
		
		return "item/campingModifyForm";
		
	}
	
	/*
	//사이트 수정페이지 보여주기
	@GetMapping(value = "/admin/site/{siteId}")
	public String siteModify(@PathVariable("siteId") Long siteId, Model model){
		SiteDto siteDto = campingService.getSiteDtl(siteId);
		model.addAttribute(siteDto)
	}
	*/
	
	//캠핑장 수정내용 업데이트
	@PostMapping(value = "/admin/camping/{campingId}" )
	public String campingUpdate(@Valid CampingFormDto campingFormDto, Model model, 
				BindingResult bindingResult, @RequestParam("campingImgFile") List<MultipartFile> campingImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "item/campingReg";
		}
		
		if(campingImgFileList.get(0).isEmpty() && campingFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수입력입니다.");
			return "item/campingReg";
		}
		
		try {
			campingService.updateCamping(campingFormDto, campingImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품 수정 중 에러가 발생했습니다.");
			return "item/campingReg";
		}
		return "redirect:/";
		
	}
	/*
	//캠핑장 삭제
	public @ResponseBody ResponseEntity deleteCamping(@PathVariable("campingId") Long campingId, Principal principal) {
		
		if(!campingService.validate)
	}
	*/
	//제휴문의 페이지
	@GetMapping(value = "/campimg/contact")
	public String itemContactUs() {
		return "item/itemContactUs";
	} 
}
