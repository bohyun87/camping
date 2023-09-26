package com.camping.dto;

import org.hibernate.validator.constraints.Length;

import com.camping.constant.Interest;
import com.camping.constant.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	
	@NotEmpty(message = "이메일은 필수입력 값입니다.")
	@Email(message = "이메일 형식에 맞게 작성하세요.")
	private String email;
	
	@NotBlank(message = "이름은 필수입력 값입니다.")
	private String name;	
	
	@NotEmpty(message="비밀번호는 필수입력 값입니다.")
	@Length(min=8, max=16, message="비밀번호는 8자~16자 사이로 입력해주세요.")
	private String password;
	
	@NotEmpty(message="전화번호는 필수입력 값입니다.")
	private String phone;
	
	private String area;
		
	private Interest interests;
	
	private Role role;
}
