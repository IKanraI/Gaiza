package util;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserFineObject implements Comparable<UserFineObject> {
	private long fine;
	private String id;

	public UserFineObject(String id, long fine) {
		this.id = id;
		this.fine = fine;
	}

	@Override
	public int compareTo(UserFineObject user) {
		return Long.compare(user.getFine(), this.fine);
	}
}
