package org.eatbacon.mongodb.benchmark.pojo;

public class UserActivity {
	long loginCount;
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
