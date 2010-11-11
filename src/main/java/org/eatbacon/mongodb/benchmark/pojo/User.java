package org.eatbacon.mongodb.benchmark.pojo;

import java.util.List;

public class User {
	String firstName;
	String lastName;
	String email;
	String ipAddress;
	
	List<UserActivity> activity;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

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
