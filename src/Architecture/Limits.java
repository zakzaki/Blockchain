package Architecture;

import java.io.Serializable;

public class Limits implements Serializable, Sendable{

	private String min,max;

	public Limits(String min, String max) {
		super();
		this.min = min;
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}
	
	
}
