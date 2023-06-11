package solunit.model;

import java.io.Serializable;

public class TimeTest implements Serializable {
	private String name;
	private long duration;

	public String getName() {
		return this.name;
	}

	public long getDuration() {
		return this.duration;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
}
