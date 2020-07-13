package com.jaenyeong.chapter_02_JPA.Jaenyeong;

public class Jaenyeong {
	private String name;

	public String getName() {
		return name;
	}

	public Jaenyeong setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "Jaenyeong{" +
				"name='" + name + '\'' +
				'}';
	}
}
