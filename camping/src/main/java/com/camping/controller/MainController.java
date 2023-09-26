package com.camping.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.camping.dto.ItemSearchDto;
import com.camping.dto.MainCampingDto;
import com.camping.service.CampingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final CampingService campingService;
	
	@GetMapping(value = "/")
	public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<MainCampingDto> campings = campingService.getMainCampingDto(itemSearchDto, pageable);
		
		model.addAttribute("campings", campings);
		model.addAttribute("itemSearchDto", itemSearchDto);
		
		
		return "main";
	}

}
