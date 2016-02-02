package com.Getters_Setters;

public class events_adapter2 {
	private int id = -1;
	private String event_title = "";
	private String event_desc = "";
	private String date_deadline = "";
	private String time_deadline = "";
	private String created_at = "";
	private String updated_at = "";
    private int priority=-1;
    private int server_id=-1;
	public int getId() {
		return id;
	}
	public String getName() {
		return event_title;
	}
	public String getDescription() {
		return event_desc;
	}
	public String getdate_deadline() {
		return date_deadline;
	}
	public String gettime_deadline() {
		return time_deadline;
	}
	public String getcreated_at() {
		return created_at;
	}
	public String getupdated_at() {
		return updated_at;
	}
	public int getpriority() {
		return priority;
	}
	public int getserver_id() {
		return server_id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.event_title = name;
	}
	public void setDescription(String description) {
		this.event_desc = description;
	}
	public void setdate_deadline(String date_dead) {
		this.date_deadline = date_dead;
	}
	public void settime_deadline(String time_dead) {
		this.time_deadline = time_dead;
	}
	public void setcreated_at(String created) {
		this.created_at = created;
	}

	public void setupdated_at(String updated) {
		this.updated_at = updated;
	}
	public void setpriority(int priority) {
		this.priority = priority;
	}
	public void setserverid(int server_id) {
		this.server_id = server_id;
	}
	@Override
	public String toString() {
		return event_title;
	}
}
