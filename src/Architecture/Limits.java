package Architecture;

import java.io.Serializable;

public class Limits implements Serializable, Sendable{

	private int min,max;

	public Limits(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}
	
	public Limits() {
		
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
/*	public boolean isvalid() {
		if(max>=min) return true;
		
		System.out.println("la limite est fausse");
		return false;
	}*/
	
	
}
