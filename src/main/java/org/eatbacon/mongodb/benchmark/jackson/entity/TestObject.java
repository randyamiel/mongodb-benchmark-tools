package org.eatbacon.mongodb.benchmark.jackson.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestObject {
	long id;
	String stringVal;
	long longVal;
	List<User> users;
	
	@XmlElement(name="_id")
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}

	@XmlElement(name="strval")
	public String getStringVal() {
		return stringVal;
	}

	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}

	@XmlElement(name="long_val")
	public long getLongVal() {
		return longVal;
	}

	public void setLongVal(long longVal) {
		this.longVal = longVal;
	}

	@XmlElement(name="users")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}