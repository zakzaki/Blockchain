package Architecture;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date_p implements Serializable, Sendable{

	private String begin, end, end_souscription;
	

	public Date_p(String begin, String end, String end_souscription) {
		super();
		this.begin = begin;
		this.end = end;
		this.end_souscription = end_souscription;
	}
	
	public Date_p() {
		
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
	
	/* public boolean isvalid(){

	        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        String cur_date = format.format(new java.util.Date());

	        java.util.Date begin_date = null;
	        java.util.Date end_date = null;
	        java.util.Date end_souscription_date =  null;
	        java.util.Date current_date =  null;

	        try {
	             begin_date = format.parse(this.begin);
	             end_date = format.parse(this.end);
	             end_souscription_date = format.parse(this.end_souscription);
	             current_date = format.parse(cur_date);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        if (begin_date.compareTo(end_date) <= 0 && current_date.compareTo(end_date) < 0
	                && end_souscription_date.compareTo(begin_date) < 0 )
	        {
	            return true;
	        }else{
	        	System.out.println("la date est fausse");
	            return false;
	        }
	    }*/
	
	
}
