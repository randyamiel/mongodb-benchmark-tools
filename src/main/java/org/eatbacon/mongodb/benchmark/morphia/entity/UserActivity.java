package org.eatbacon.mongodb.benchmark.morphia.entity;

import com.google.code.morphia.annotations.Entity;

import com.google.code.morphia.annotations.Property;

@Entity(value="activity", noClassnameStored=true)
public class UserActivity {
	@Property("login_count")
	long loginCount;

	@Property("referrer")
	String referrer;
	
	public long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(long loginCount) {
		this.loginCount = loginCount;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	
	@Override
	public String toString() {
		return "UserActivity [loginCount=" + loginCount + ", referrer="
				+ referrer + "]";
	}
}