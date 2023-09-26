package com.camping.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.camping.constant.CampGround;
import com.camping.constant.CampingType;
import com.camping.constant.Region;
import com.camping.constant.SiteSellStatus;
import com.camping.dto.CampingFormDto;
import com.camping.dto.CampingListDto;
import com.camping.dto.ItemSearchDto;
import com.camping.dto.MainCampingDto;
import com.camping.dto.QCampingListDto;
import com.camping.dto.QMainCampingDto;
import com.camping.entity.Camping;
import com.camping.entity.QCamping;
import com.camping.entity.QCampingImg;
import com.camping.entity.QCampingSite;
import com.camping.entity.QCampingSiteImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class CampingRepositoryCustomImpl implements CampingRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	private CampingRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	} 
	
	//검색기능구현
	
	//제휴상태 검색
	private BooleanExpression searchSiteSellStatusEq(SiteSellStatus searchSiteSellStatus) {
		
		return searchSiteSellStatus == null ? null : QCamping.camping.siteSellStatus.eq(searchSiteSellStatus);
	}
	
	//캠핑환경으로 검색
	private BooleanExpression searchCampGroundEq(CampGround searchCampGround) {
		
		return searchCampGround == null ? null : QCamping.camping.campGround.eq(searchCampGround);
	}
	
	//캠핑유형으로 검색
	private BooleanExpression searchCampingTypeEq(CampingType searchCampingType) {
		
		return searchCampingType == null ? null : QCamping.camping.campingType.eq(searchCampingType);
	}
	
	//지역으로 검색
	private BooleanExpression searchRegionEq(Region searchRegion) {
		
		return searchRegion == null ? null : QCamping.camping.region.eq(searchRegion);
	}
	
	//검색어로 검색
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("campingName", searchBy)) {			
			return QCamping.camping.campingName.like("%" + searchQuery + "%");			
		} else if(StringUtils.equals("address", searchBy)) {
			return QCamping.camping.address.like("%" + searchQuery + "%");		
		}
		return null;
	}
	
	//관리페이지
	@Override
	public Page<CampingListDto> getAdminMngPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		
		QCamping camping = QCamping.camping;
		QCampingImg campingImg = QCampingImg.campingImg;
		
		List<CampingListDto> content = queryFactory.select(
														new QCampingListDto(
															camping.id,
															camping.campingName,
															camping.region,
															camping.campGround,
															camping.campingType,
															camping.siteSellStatus,
															campingImg.camImgUrl)
														)
													.from(campingImg)
													.join(campingImg.camping, camping)
													.where(campingImg.camRepImgYn.eq("Y"))
													.where(campingNameLike(itemSearchDto.getSearchQuery()))
													.where(searchSiteSellStatusEq(itemSearchDto.getSearchSiteSellStatus()),
															searchCampGroundEq(itemSearchDto.getSearchCampGround()),
															searchCampingTypeEq(itemSearchDto.getSearchCampingType()),
															searchRegionEq(itemSearchDto.getSearchRegion()),								
															searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
													.orderBy(camping.id.asc())
													.offset(pageable.getOffset())
													.limit(pageable.getPageSize())
													.fetch();										
		
		long total = queryFactory.select(Wildcard.count) 
				.from(QCamping.camping)
				.where(searchSiteSellStatusEq(itemSearchDto.getSearchSiteSellStatus()),
						searchCampGroundEq(itemSearchDto.getSearchCampGround()),
						searchCampingTypeEq(itemSearchDto.getSearchCampingType()),
						searchRegionEq(itemSearchDto.getSearchRegion()),
						searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
				.fetchOne();
						
				
		return new PageImpl<>(content, pageable, total);
	}
	
	private BooleanExpression campingNameLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QCamping.camping.campingName.like("%" + searchQuery + "%");
	}

	//캠핑페이지
	@Override
	public Page<CampingListDto> getCampingPage(ItemSearchDto itemSearchDto, Pageable pageable) {

		QCamping camping = QCamping.camping;
		QCampingImg campingImg = QCampingImg.campingImg;
		
		List<CampingListDto> content = queryFactory
											.select(
												new QCampingListDto(
												camping.id,
												camping.campingName,
												camping.campingSubName,
												camping.region,
												camping.address,
												camping.tel,
												camping.campingDetail,
												campingImg.camImgUrl												
												)
											)
											.from(campingImg)
											.join(campingImg.camping, camping)
											.where(campingImg.camRepImgYn.eq("Y"))
											.where(searchCampGroundEq(itemSearchDto.getSearchCampGround()),
													searchCampingTypeEq(itemSearchDto.getSearchCampingType()),
													searchRegionEq(itemSearchDto.getSearchRegion()),								
													searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
											.orderBy(camping.id.asc())
											.offset(pageable.getOffset())
											.limit(pageable.getPageSize())
											.fetch();
		
		long total = queryFactory.select(Wildcard.count)
								 .from(campingImg)
								 .join(campingImg.camping, camping)
								 .where(campingImg.camRepImgYn.eq("Y"))
								 .where(searchCampGroundEq(itemSearchDto.getSearchCampGround()),
										searchCampingTypeEq(itemSearchDto.getSearchCampingType()),
										searchRegionEq(itemSearchDto.getSearchRegion()),								
										searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
								 .fetchOne();
		
		
		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public Page<MainCampingDto> getMainCampingDto(ItemSearchDto itemSearchDto, Pageable pageable) {
		
		QCamping camping =QCamping.camping;
		QCampingImg campingImg = QCampingImg.campingImg;
		
		List<MainCampingDto> content = queryFactory.select(
											new QMainCampingDto(
														camping.id,
														camping.campingName,
														camping.campingSubName,
														camping.region,
														camping.address,
														campingImg.camImgUrl)
												)
											.from(campingImg)
											.join(campingImg.camping, camping)
											.where(campingImg.camRepImgYn.eq("Y"))
											.where(campingNameLike(itemSearchDto.getSearchQuery()))
											.orderBy(camping.id.desc())
											.offset(pageable.getOffset())
											.limit(pageable.getPageSize())
											.fetch();
		
		long total = queryFactory.select(Wildcard.count)
								 .from(campingImg)
								 .join(campingImg.camping, camping)
								 .where(campingImg.camRepImgYn.eq("Y"))
								 .where(campingNameLike(itemSearchDto.getSearchQuery()))
								 .fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}
	
	
	
	
	
}
