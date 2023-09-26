package com.camping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findByEmail(String email);
	
	Member findByPhone(String phone);
	
	
}
