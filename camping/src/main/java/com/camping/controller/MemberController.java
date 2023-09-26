package com.camping.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camping.dto.MemberFormDto;
import com.camping.entity.Member;
import com.camping.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	//문의하기
	@GetMapping(value = "/members/qna")
	public String qna() {
		return "member/qna";
	}	
	
	//로그인화면
	@GetMapping(value = "/members/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
	//회원가입 화면
	@GetMapping(value = "/members/join")
	public String joinMember(Model model) {		
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberJoinForm";
	}	
	
	//회원가입하기
	@PostMapping(value = "/members/join")
	public String joinMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			
			return "member/memberJoinForm";
		}
		
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);			
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberJoinForm";
		}
		
		return "redirect:/";
	}	
	
	
	@ModelAttribute("interests")
	public Map<String, String> interests(){
		Map<String, String> interests = new LinkedHashMap<String, String>();
		interests.put("CAMPING", "노지캠핑");
		interests.put("GLAMPING", "글램핑");
		interests.put("CARAVAN", "카라반");
		
		return interests;
	}
	
	
	//로그인 실패했을때
	@GetMapping(value = "/members/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberloginForm";
	}
}
