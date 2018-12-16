package Architecture;

public class Date_p {

	private String begin, end, end_souscription;
	

	public Date_p(String begin, String end, String end_souscription) {
		super();
		this.begin = begin;
		this.end = end;
		this.end_souscription = end_souscription;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getEnd_souscription() {
		return end_souscription;
	}

	public void setEnd_souscription(String end_souscription) {
		this.end_souscription = end_souscription;
	}
	
	
}
