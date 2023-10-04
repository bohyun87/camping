package com.camping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.entity.ContactUs;

public interface ContactUsRepository extends JpaRepository<ContactUs, Long>, ContactUsRepositoryCustom{

}
