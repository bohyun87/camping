	package com.camping.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camping.entity.Member;
import com.camping.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email);
		
		if(member == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}	
	
	private final MemberRepository memberRepository;
		
	//이메일, 전화번호 중복체크
	private void validateDuplicateMember(Member member) {
		Member findemail = memberRepository.findByEmail(member.getEmail());
		Member findphone = memberRepository.findByPhone(member.getPhone());
		
		
		if(findemail != null) {
			throw new IllegalStateException("이미 가입된 email입니다.");
		}
		
		if(findphone != null) {
			throw new IllegalStateException("이미 가입된 전화번호입니다.");
		}
		
	}

	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		Member saveMember = memberRepository.save(member);
		return saveMember;
	}

}
