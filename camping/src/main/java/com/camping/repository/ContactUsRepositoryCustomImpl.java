package com.camping.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.camping.constant.ContactStatus;
import com.camping.dto.ContactSearchDto;
import com.camping.dto.ContactUsDto;
import com.camping.entity.ContactUs;
import com.camping.entity.QContactUs;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ContactUsRepositoryCustomImpl implements ContactUsRepositoryCustom{
	
	private JPAQueryFactory queryFactory;
	
	public ContactUsRepositoryCustomImpl (EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	//검색기능구현
	
	//답변상태 검색
	private BooleanExpression searchContactStatusEq(ContactStatus searchContactStatus) {
		return searchContactStatus == null ? null : QContactUs.contactUs.contactStatus.eq(searchContactStatus);
	}
	
	//캠핑장명 검색
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("campingName", searchBy)) {
			return QContactUs.contactUs.campingName.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("createBy", searchBy)) {
			return QContactUs.contactUs.createBy.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	@Override
	public Page<ContactUs> getItemContactUsList(Pageable pageable) {

		List<ContactUs> content = queryFactory.selectFrom(QContactUs.contactUs)
											  .orderBy(QContactUs.contactUs.id.desc())
											  .offset(pageable.getOffset())
											  .limit(pageable.getPageSize())
											  .fetch();		
		
		long total = queryFactory.select(Wildcard.count)
								 .from(QContactUs.contactUs)
								 .fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public Page<ContactUs> getAdminContactUsList(ContactSearchDto contactSearchDto, Pageable pageable) {
		
		List<ContactUs> content = queryFactory.selectFrom(QContactUs.contactUs)
											  .where(searchContactStatusEq(contactSearchDto.getSearchContactStatus()),
													 searchByLike(contactSearchDto.getSearchBy(), contactSearchDto.getSearchQuery()))
											  .orderBy(QContactUs.contactUs.id.desc())
											  .offset(pageable.getOffset())
											  .limit(pageable.getPageSize())
											  .fetch();		

		long total = queryFactory.select(Wildcard.count)
								 .from(QContactUs.contactUs)
								 .fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

}
