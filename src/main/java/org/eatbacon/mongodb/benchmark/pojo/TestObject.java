package org.eatbacon.mongodb.benchmark.pojo;

import java.util.List;

public class TestObject {
	String stringVal;
	long longVal;
	List<User> users;

	public String getStringVal() {
		return stringVal;
	}

	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}

	public long getLongVal() {
		return longVal;
	}

	public void setLongVal(long longVal) {
		this.longVal = longVal;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "TestObject [longVal=" + longVal + ", stringVal=" + stringVal
				+ ", users=" + users + "]";
	}
}