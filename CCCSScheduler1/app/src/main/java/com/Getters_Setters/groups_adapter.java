package com.Getters_Setters;

public class groups_adapter {
	
	private int id = -1;
	private String groupname = "";
	private String description = "";
	private int leaderid = -1;
	private String created_at = "";
	private String updated_at = "";
	
	public int getId() {
		return id;
	}
	public String getName() {
		return groupname;
	}
	public String getDescription() {
		return description;
	}
	public int getleaderid() {
		return leaderid;
	}
	public String getcreated_at() {
		return created_at;
	}
	public String getupdated_at() {
		return updated_at;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.groupname = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setleaderid(int leaderid) {
		this.leaderid = leaderid;
	}

	public void setcreated_at(String created) {
		this.created_at = created;
	}
	
	public void setupdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	@Override
	public String toString() {
		return groupname;
	}
}
