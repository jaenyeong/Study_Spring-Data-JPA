package com.jaenyeong.chapter_01_basic.association;

import java.util.Set;

public class Study {
	private User owner;
	private Set<User> attenders;

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Set<User> getAttenders() {
		return attenders;
	}

	public void setAttenders(Set<User> attenders) {
		this.attenders = attenders;
	}

	public void hello() {
		getOwner().getMyStudy().forEach(Study::getOwner);
	}
}
