package com.camping.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseTimeEntity {

	@CreatedDate
	@Column(updatable = false)
	public LocalDateTime regTime;

	@LastModifiedDate
	public LocalDateTime updateTime;
	

}
