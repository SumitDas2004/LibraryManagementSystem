package com.minorproject.model;

import java.io.Serializable;

public class UpdateAuthorRequestModel  implements Serializable{
	private String name;
	
	private Integer age;
	
	private String email;
	
	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public UpdateAuthorRequestModel(String name, Integer age, String email, String country) {
		super();
		this.name = name;
		this.age = age;
		this.email = email;
		this.country = country;
	}

	public UpdateAuthorRequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
