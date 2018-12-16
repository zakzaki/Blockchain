package Architecture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Payload {
	
	private String name;
	private String description;
	private Date_p date;
	private String location;
	private Limits limits;
	
	
	public Payload(String name, String description, Date_p date, String location, Limits limits) {
		super();
		this.name = name;
		this.description = description;
		this.date = date;
		this.location = location;
		this.limits = limits;
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
	
}
