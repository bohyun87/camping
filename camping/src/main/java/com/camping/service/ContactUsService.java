package com.camping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.camping.dto.ContactSearchDto;
import com.camping.dto.ContactUsDto;
import com.camping.dto.ContactUsImgDto;
import com.camping.entity.ContactUs;
import com.camping.entity.ContactUsImg;
import com.camping.entity.Member;
import com.camping.repository.ContactUsImgRepository;
import com.camping.repository.ContactUsRepository;
import com.camping.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactUsService {

	private final ContactUsRepository contactUsRepository;
	private final ContactUsImgRepository contactUsImgRepository;
	private final ContactUsImgService contactUsImgService;
	private final MemberRepository memberRepository;
	
	//제휴문의 목록페이지
	public Page<ContactUs> getItemContactUsList(Pageable pageable){
		
		Page<ContactUs> contactUsPage = contactUsRepository.getItemContactUsList(pageable);
		
		return contactUsPage;
	}
	
	//제휴문의 등록하기
	public Long saveContactUs(ContactUsDto contactUsDto, List<MultipartFile> contactUsImgFileList) throws Exception {
		
		ContactUs contactUs = contactUsDto.createContactUs(contactUsDto);
		contactUsRepository.save(contactUs);
		
		for(int i = 0; i < contactUsImgFileList.size(); i++) {
			ContactUsImg contactUsImg = new ContactUsImg();
			contactUsImg.setContactUs(contactUs);
			
			if(i == 0) {
				contactUsImg.setRepimgYn("Y");
			} else {
				contactUsImg.setRepimgYn("N");
			}
			contactUsImgService.saveContactUsImg(contactUsImg, contactUsImgFileList.get(i));
		}
		
		return contactUs.getId();
	}
	
	//제휴문의 내용 보여주기
	@Transactional(readOnly = true)
	public ContactUsDto getContactDtl(Long contactUsId) {
		List<ContactUsImg> contactUsList = contactUsImgRepository.findByContactUsIdOrderByIdAsc(contactUsId);
		
		List<ContactUsImgDto> contactUsImgDtoList = new ArrayList<>();
		for(ContactUsImg contactUsImg : contactUsList) {
			ContactUsImgDto contactUsImgDto = ContactUsImgDto.of(contactUsImg);
			
			contactUsImgDtoList.add(contactUsImgDto);
		}
		
		ContactUs contactUs = contactUsRepository.findById(contactUsId).orElseThrow(EntityNotFoundException::new);
		
		ContactUsDto contactUsDto = ContactUsDto.of(contactUs);
		
		contactUsDto.setContactUsImgDtoList(contactUsImgDtoList);
		
		return contactUsDto;
	}
	
	//제휴문의 수정하기
	public Long updateContactUs(ContactUsDto contactUsDto, List<MultipartFile> contactImgFileList) throws Exception{
		ContactUs contactUs = contactUsRepository.findById(contactUsDto.getId()).orElseThrow(EntityNotFoundException::new);
		
		contactUs.updateContactUs(contactUsDto);
		
		List<Long> contactImgIds = contactUsDto.getContactUsImgIds();
		
		for(int i=0; i<contactImgFileList.size(); i++) {
			contactUsImgService.updateContactUsImg(contactImgIds.get(i), contactImgFileList.get(i));
		}
		
		return contactUs.getId();
	}
	
	//제휴문의 관리자페이지
	public Page<ContactUs> getAdminContactUsList(ContactSearchDto contactSearchDto, Pageable pageable){
		
		Page<ContactUs> contactUsPage = contactUsRepository.getAdminContactUsList(contactSearchDto, pageable);
		
		return contactUsPage;
	}

}
