package com.jaenyeong.chapter_01_basic.association;

import java.util.List;

public class User {
	private List<Study> myStudy;

	public List<Study> getMyStudy() {
		return myStudy;
	}

	public User setMyStudy(List<Study> myStudy) {
		this.myStudy = myStudy;
		return this;
	}
}
