package com.camping.entity;

import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;


@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity {

	@CreatedBy
	@Column(updatable = false)
	public String createBy;
	
	@LastModifiedBy
	public String modifiedBy;
}
