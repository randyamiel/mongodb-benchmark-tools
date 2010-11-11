package org.eatbacon.mongodb.benchmark.jackson.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="activity")
public class UserActivity {
	long loginCount;
	String referrer;

	@XmlElement(name = "login_count")
	public long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(long loginCount) {
		this.loginCount = loginCount;
	}

	@XmlElement(name = "referrer")
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
