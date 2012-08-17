package br.com.digilabs.domain;

import javax.persistence.Entity;

@Entity
public class Address extends BasicEntity {
	
	private static final long serialVersionUID = -2814681823692836451L;

	private String address;
	private String city;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}	

}
