package com.camping.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.camping.constant.Interest;
import com.camping.constant.Role;
import com.camping.dto.MemberFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity{

	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	private String name;
	
	private String password;
	
	private String phone;
	
	@Column(nullable = true)
	private String area;
	
	@Enumerated(EnumType.STRING)
	private Interest interests;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	//회원가입 form 데이터 가져오기
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setPhone(memberFormDto.getPhone());
		member.setArea(memberFormDto.getArea());
		member.setInterests(memberFormDto.getInterests());
		member.setPassword(password);
		
		member.setRole(Role.USER);
		
		return member;
	}
	
	
	
}
