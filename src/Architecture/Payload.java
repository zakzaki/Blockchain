package Architecture;

import java.io.Serializable;

public class Payload implements Serializable, Sendable{
	
	private String name;
	private String description;
	private Date_p date;
	private String location;
	private Limits limits;
	private String event_hash;
	
	public Payload(String name, String description, Date_p date, String location, Limits limits) {
		super();
		this.name = name;
		this.description = description;
		this.date = date;
		this.location = location;
		this.limits = limits;
	}
	
	
	public Payload(String event_hash) {
		this.event_hash=event_hash;
	}
	
	public Payload() {
	
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date_p getDate() {
		return date;
	}
	public void setDate(Date_p date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Limits getLimits() {
		return limits;
	}
	public void setLimits(Limits limits) {
		this.limits = limits;
	}
	
	
	public String getEvent_hash() {
		return event_hash;
	}

	public void setEvent_hash(String event_hash) {
		this.event_hash = event_hash;
	}
	
	/*public boolean isvalid() {
		if(date.isvalid() && limits.isvalid()) return true;
		return false;
	}*/

	
}
