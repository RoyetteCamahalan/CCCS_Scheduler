package com.Getters_Setters;

public class member_getset {
		private int id = -1;
		private String fullname = "";
		private String address = "";
		public int getId() {
			return id;
		}
		public String getName() {
			return fullname;
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
}
