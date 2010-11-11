package org.eatbacon.mongodb.benchmark.jackson.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eatbacon.mongodb.benchmark.jackson.entity.UserActivity;

@XmlRootElement
public class User {
	String firstName;
	String lastName;
	String email;
	String ipAddress;
	
	List<UserActivity> activity;
	
	@XmlElement(name="first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement(name="last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement(name="ip_addres")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@XmlElement(name="activity")
	public List<UserActivity> getActivity() {
		return activity;
	}

	public void setActivity(List<UserActivity> activity) {
		this.activity = activity;
	}

	@Override
	public String toString() {
		return "User [activity=" + activity + ", email=" + email
				+ ", firstName=" + firstName + ", ipAddress=" + ipAddress
				+ ", lastName=" + lastName + "]";
	}
}
