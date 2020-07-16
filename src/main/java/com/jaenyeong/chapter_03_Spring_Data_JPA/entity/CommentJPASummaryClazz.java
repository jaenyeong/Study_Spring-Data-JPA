package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

//@Data
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

	public String getComment() {
		return comment;
	}

	public CommentJPASummaryClazz setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public int getUp() {
		return up;
	}

	public CommentJPASummaryClazz setUp(int up) {
		this.up = up;
		return this;
	}

	public int getDown() {
		return down;
	}

	public CommentJPASummaryClazz setDown(int down) {
		this.down = down;
		return this;
	}
}
