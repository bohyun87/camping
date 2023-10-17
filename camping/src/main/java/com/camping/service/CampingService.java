package com.camping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.camping.dto.CampingFormDto;
import com.camping.dto.CampingImgDto;
import com.camping.dto.CampingListDto;
import com.camping.dto.ItemSearchDto;
import com.camping.dto.MainCampingDto;
import com.camping.dto.SiteDto;
import com.camping.dto.SiteImgDto;
import com.camping.entity.Camping;
import com.camping.entity.CampingImg;
import com.camping.entity.CampingSite;
import com.camping.entity.CampingSiteImg;
import com.camping.repository.CampingImgRepository;
import com.camping.repository.CampingRepository;
import com.camping.repository.SiteImgRepository;
import com.camping.repository.SiteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CampingService {
	
	private final CampingRepository campingRepository;
	private final CampingImgRepository campingImgRepository;
	
	private final SiteRepository siteRepository;
	private final SiteImgRepository siteImgRepository;
	
	private final CampingImgService campingImgService;
	
	
	//camping 테이블 상품등록
	public Long saveCamping(CampingFormDto campingFormDto, List<MultipartFile> campingImgFileLsit) throws Exception {
		
		Camping camping = campingFormDto.createCamping();
		campingRepository.save(camping);
		
		for(int i=0; i<campingImgFileLsit.size(); i++) {
			
			CampingImg campingImg = new CampingImg();
			campingImg.setCamping(camping);
			
			if(i == 0) {
				campingImg.setCamRepImgYn("Y");
			} else {
				campingImg.setCamRepImgYn("N");
			}
			campingImgService.saveCampingImg(campingImg, campingImgFileLsit.get(i));
		}
		return camping.getId();
	}
	
	//site.html 캠핑장명 목록(오름차순) 불러오기
	public List<Camping> listAll(){
		List<Camping> campingList = campingRepository.findAll(Sort.by(Sort.Direction.ASC, "campingName"));
		
		List<CampingFormDto> campingFormDtoList = new ArrayList<>();
		
		for(Camping camping : campingList) {
			
			CampingFormDto campingFormDto = CampingFormDto.of(camping);
			
			campingFormDtoList.add(campingFormDto);
		}
		
		return campingList;
	}
	
	//site 테이블 상품등록
		public Long saveSite(SiteDto siteDto, List<MultipartFile> siteImgFileList) throws Exception {
			
			CampingSite site = siteDto.createSite();			
			siteRepository.save(site);
			
			for(int i=0; i<siteImgFileList.size(); i++) {
				
				CampingSiteImg siteImg = new CampingSiteImg();
				siteImg.setCampingSite(site);
				
				if(i == 0) {
					siteImg.setSiteRepImgYn("Y");
				} else {
					siteImg.setSiteRepImgYn("N");
				}
				campingImgService.saveSiteImg(siteImg, siteImgFileList.get(i));
			}
			return site.getId();
		}

	//캠핑장데이터 가져오기
	@Transactional(readOnly = true)
	public CampingFormDto getCampingDtl(Long campingId) {
		
		//캠핑장이미지 테이블에서 이미지가져오기
		List<CampingImg> campingImgList = campingImgRepository.findByCampingIdOrderByIdAsc(campingId);
				
			//campingImg 엔티티 campingImgDto 변환
			List<CampingImgDto> campingImgDtoList = new ArrayList<>();
			for(CampingImg campingImg : campingImgList) {
				CampingImgDto campingImgDto = CampingImgDto.of(campingImg);
				
				campingImgDtoList.add(campingImgDto);
			}
		//camping 테이블에 있는 데이터 가져오기
		Camping camping = campingRepository.findById(campingId).orElseThrow(EntityNotFoundException::new);
			
		//camping 앤티티 campingFormDto 변환
		CampingFormDto campingFormDto = CampingFormDto.of(camping);
		
		//CampingFormDto 에 이미지정보(campingImgDtoList) 저장하기
		campingFormDto.setCampingImgDtoList(campingImgDtoList);
		
		//site가져오기
		List<CampingSite> site = siteRepository.findByCampingId(campingId);
		
		//site -> dto 리스트로 변환
		List<SiteDto> siteDtoList = new ArrayList<>();
		for(CampingSite campingSite : site) {
			SiteDto siteDto = SiteDto.of(campingSite);
			
			List<CampingSiteImg> campingSiteImgList = siteImgRepository.findByCampingSiteIdOrderByIdAsc(campingSite.getId());
			List<SiteImgDto> siteImgDtoList = new ArrayList<>();
			
			for(CampingSiteImg campingSiteImg : campingSiteImgList) {
				SiteImgDto siteImgDto = SiteImgDto.of(campingSiteImg);
				siteImgDtoList.add(siteImgDto);
			}
			
			siteDto.setSiteImgDtoList(siteImgDtoList);
			
			siteDtoList.add(siteDto);

		}
	
		campingFormDto.setSiteDtoList(siteDtoList);				
		
		return campingFormDto;
	}	
		
	//캠핑 사이트데이터 가져오기
	@Transactional(readOnly = true)
	public SiteDto getSiteDtl(Long siteId) {
		
		//이미지 테이블에서 이미지가져오기
		List<CampingSiteImg> siteImgList = siteImgRepository.findByCampingSiteIdOrderByIdAsc(siteId);
				
			//siteImg 엔티티 siteImgDto 변환
			List<SiteImgDto> siteImgDtoList = new ArrayList<>();
			for(CampingSiteImg siteImg : siteImgList) {
				SiteImgDto siteImgDto = SiteImgDto.of(siteImg);
				
				siteImgDtoList.add(siteImgDto);
			}
			
		//site 테이블에 있는 데이터 가져오기
		CampingSite site = siteRepository.findById(siteId).orElseThrow(EntityNotFoundException::new);
			
			//site 앤티티 siteDto 변환
			SiteDto siteDto = SiteDto.of(site);
			
		//siteDto 에 이미지정보(siteImgDtoList) 저장하기
		siteDto.setSiteImgDtoList(siteImgDtoList);
		
		/*
		// available 가져오기
		List<AvailableDate> availableDates = availableDateRepository.findByCampingSiteIdOrderByIdAsc(siteId);
				
		//available 엔티티 Dto 변환
		List<AvailableDateDto> availableDateDtoList = new ArrayList<>();
		for(AvailableDate availableDate : availableDates) {
			AvailableDateDto availableDato = AvailableDateDto.of(availableDate);
			
			availableDateDtoList.add(availableDato);
		}
		
		siteDto.setAvailableDateDtoList(availableDateDtoList);*/
				
		return siteDto;
	}
	
	//수정하기
	public Long updateCamping(CampingFormDto campingFormDto, List<MultipartFile> campingImgFileList) throws Exception {
		Camping camping = campingRepository.findById(campingFormDto.getId())
									.orElseThrow(EntityNotFoundException::new);
		
		camping.updateCamping(campingFormDto);
		
		List<Long> campingImgIds = campingFormDto.getCampingImgIds();
		
		for(int i=0; i<campingImgFileList.size(); i++) {
			campingImgService.updateCampingImg(campingImgIds.get(i), campingImgFileList.get(i));
		}
		return camping.getId();
	}
	/*
	//본인확인
	public boolean validateCamping(Long campingId, String email) {
		
	}
	*/
		
	//campingMng.html 페이징
	@Transactional(readOnly = true)
	public Page<CampingListDto> getAdminMngPage(ItemSearchDto itemSearchDto, Pageable pageable){
		Page<CampingListDto> campingPage = campingRepository.getAdminMngPage(itemSearchDto, pageable);
		return campingPage;
	}

	//camping.html 페이징
	public Page<CampingListDto> getCampingPage(ItemSearchDto itemSearchDto, Pageable pageable){
		Page<CampingListDto> campingPage = campingRepository.getCampingPage(itemSearchDto, pageable);
		
		return campingPage;
	}
	
	//main.html 페이징
	public Page<MainCampingDto> getMainCampingDto(ItemSearchDto itemSearchDto, Pageable pageable){
		Page<MainCampingDto> campingMainPage = campingRepository.getMainCampingDto(itemSearchDto, pageable);
		
		return campingMainPage;
	}
	
	
}
