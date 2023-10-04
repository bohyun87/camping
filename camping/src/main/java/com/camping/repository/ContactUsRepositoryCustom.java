package com.camping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.camping.dto.ContactSearchDto;
import com.camping.entity.ContactUs;

public interface ContactUsRepositoryCustom {
	Page<ContactUs> getItemContactUsList(Pageable pageable);

	Page<ContactUs> getAdminContactUsList(ContactSearchDto contactSearchDto, Pageable pageable);
}
