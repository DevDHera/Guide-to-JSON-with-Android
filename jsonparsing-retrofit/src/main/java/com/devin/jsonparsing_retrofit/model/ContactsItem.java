package com.devin.jsonparsing_retrofit.model;

import com.google.gson.annotations.SerializedName;

public class ContactsItem{

	@SerializedName("address")
	private String address;

	@SerializedName("gender")
	private String gender;

	@SerializedName("phone")
	private Phone phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setPhone(Phone phone){
		this.phone = phone;
	}

	public Phone getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"ContactsItem{" + 
			"address = '" + address + '\'' + 
			",gender = '" + gender + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}