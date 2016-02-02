package com.Getters_Setters;

public class Notif_getset {
	private int id = -1;
	private int groupid=-1;
	private String groupname = "";
	private String description = "";
	private String leadername = "";
	private String notifdate = "";
	private int Status=-1;
	private int notiftype=-1;
	public int getId() {
		return id;
	}
	public int getgroupid() {
		return groupid;
	}
	public String getName() {
		return groupname;
	}
	public String getDescription() {
		return description;
	}
	public String getleadername() {
		return leadername;
	}
	public String getnotifdate() {
		return notifdate;
	}
	public int getstatus() {
		return Status;
	}
	public int getnotiftype() {
		return notiftype;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setgroupid(int groupid) {
		this.groupid = groupid;
	}
	public void setName(String name) {
		this.groupname = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setleadername(String leadername) {
		this.leadername = leadername;
	}

	public void setnotifdate(String notifdate) {
		this.notifdate = notifdate;
	}
	public void setstatus(int Status) {
		this.Status = Status;
	}
	public void setnotiftype(int notiftype) {
		this.notiftype = notiftype;
	}
	@Override
	public String toString() {
		return groupname;
	}
}
