package com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class WebPost {
	@Id @GeneratedValue
	private Long id;
	private String title;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
}
