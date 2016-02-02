package com.Getters_Setters;

public class users_getset {
	private int id = -1;
	private String fullname = "";
	private String address = "";
	private int Status=-1;
	public int getId() {
		return id;
	}
	public String getName() {
		return fullname;
	}
	public int getStatus() {
		return Status;
	}
	public String getAddress() {
		return address;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String fname, String mname, String lname) {
		this.fullname=fname+" "+mname+" "+lname;
	}
	public void setAddress(String address) {
		this.address = address;
	}
		@Override
	public String toString() {
		return fullname;
	}
		public void setStatus(int status) {
			this.Status = status;
		}
}
