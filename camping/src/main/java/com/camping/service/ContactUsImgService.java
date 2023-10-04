package com.camping.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.camping.entity.ContactUsImg;
import com.camping.repository.ContactUsImgRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactUsImgService {

	@Value("${contactUsLocation}")
	private String contactImgLocation;
	private final ContactUsImgRepository contactUsImgRepository;
	private final FileService fileService;
	
	public void saveContactUsImg(ContactUsImg contactUsImg, MultipartFile contactUsImgFile) throws Exception {
		
		String oriImgName = contactUsImgFile.getOriginalFilename();  //파일이름 -> 이미지1.jpg
		String contactImgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			contactImgName = fileService.uploadFile(contactImgLocation, oriImgName, contactUsImgFile.getBytes());
			imgUrl = "/images/contactUs/" + contactImgName;
		}
		
		contactUsImg.updateContactUsImg(oriImgName, contactImgName, imgUrl);
		
		contactUsImgRepository.save(contactUsImg);
	}
	
	public void updateContactUsImg(Long contactUsId, MultipartFile contactUsImgFile) throws Exception {
		if(!contactUsImgFile.isEmpty()) {
			ContactUsImg savedContactUsImg = contactUsImgRepository.findById(contactUsId).orElseThrow(EntityNotFoundException::new);

			if(!StringUtils.isEmpty(savedContactUsImg.getContactImgName())) {
				fileService.deleteFile(contactImgLocation + "/" + savedContactUsImg.getContactImgName());
			}
			
			String oriImgName = contactUsImgFile.getOriginalFilename();
			String contactImgName = fileService.uploadFile(contactImgLocation, oriImgName, contactUsImgFile.getBytes());
			String imgUrl = "/images/contactUs/" + contactImgName;
			
			savedContactUsImg.updateContactUsImg(oriImgName, contactImgName, imgUrl);
		}
		
	}
	
}
