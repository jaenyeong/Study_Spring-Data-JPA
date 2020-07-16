package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import lombok.Data;

@Data
public class CommentJPASummaryClazz {
	private String comment;
	private int up;
	private int down;

	public CommentJPASummaryClazz(String comment, int up, int down) {
		this.comment = comment;
		this.up = up;
		this.down = down;
	}

	public String getVotes() {
		return this.up + " " + this.down;
	}
}
