package org.eatbacon.mongodb.benchmark.morphia.entity;

import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

@Entity
public class TestObject {
	@Id
	long id;
	@Property("strval")
	String stringVal;
	@Property("long_val")
	long longVal;
	
	@Embedded
	List<User> users;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
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
		return "MorphiaEntity [id=" + id + ", longVal=" + longVal
				+ ", stringVal=" + stringVal + ", users=" + users + "]";
	}
}