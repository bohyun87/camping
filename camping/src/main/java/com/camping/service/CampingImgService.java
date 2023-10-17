package com.camping.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.camping.entity.CampingImg;
import com.camping.entity.CampingSiteImg;
import com.camping.repository.CampingImgRepository;
import com.camping.repository.SiteImgRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CampingImgService {
	
	
	@Value("${campingImgLocation}")
	private String campingImgLocation;
	
	private final CampingImgRepository campingImgRepository;
	private final SiteImgRepository siteImgRepository;
	private final FileService fileService;
	
	//캠핑
	public void saveCampingImg(CampingImg campingImg, MultipartFile campingImgFile) throws Exception {
		String oriImgName = campingImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(campingImgLocation, oriImgName, campingImgFile.getBytes());
			imgUrl = "/image/item/" + imgName;
		}
		
		campingImg.updateCampingImg(oriImgName, imgName, imgUrl);
		
		campingImgRepository.save(campingImg);
	}
	
	public void updateCampingImg(Long campingImgId, MultipartFile campingImgFile) throws Exception {
		
		if(!campingImgFile.isEmpty()) {
			CampingImg savedCampingImg = campingImgRepository.findById(campingImgId)
												.orElseThrow(EntityNotFoundException::new);
			
			if(!StringUtils.isEmpty(savedCampingImg.getCamImgName())) {
				fileService.deleteFile(campingImgLocation + "/" + savedCampingImg.getCamImgName());
			}
			
			String oriImgName = campingImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(campingImgLocation, oriImgName, campingImgFile.getBytes());
			String imgUrl = "/image/item" + imgName;
			
			savedCampingImg.updateCampingImg(oriImgName, imgName, imgUrl);
		}
		
	}
	
	//사이트
	public void saveSiteImg(CampingSiteImg siteImg, MultipartFile siteImgFileList) throws Exception {
		String oriImgName = siteImgFileList.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(campingImgLocation, oriImgName, siteImgFileList.getBytes());
			imgUrl = "/image/item/" + imgName;
		}
		
		siteImg.updateSiteImg(oriImgName, imgName, imgUrl);
		
		siteImgRepository.save(siteImg);
	}

	public void updateSiteImg(Long CampingSiteId, MultipartFile siteImgFile) throws Exception {
		
		if(!siteImgFile.isEmpty()) {
			CampingSiteImg savedSiteImg = siteImgRepository.findById(CampingSiteId)
												.orElseThrow(EntityNotFoundException::new);
			
			if(!StringUtils.isEmpty(savedSiteImg.getSiteImgName())) {
				fileService.deleteFile(campingImgLocation + "/" + savedSiteImg.getSiteImgName());
			}
			
			String oriImgName = siteImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(campingImgLocation, oriImgName, siteImgFile.getBytes());
			String imgUrl = "/image/item/" + imgName;
			
			savedSiteImg.updateSiteImg(oriImgName, imgName, imgUrl);
		}
		
	}
	
}
