package com.camping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.entity.ContactUsImg;

public interface ContactUsImgRepository extends JpaRepository<ContactUsImg, Long> {
	
	List<ContactUsImg> findByContactUsIdOrderByIdAsc(Long contactUsId);

}
