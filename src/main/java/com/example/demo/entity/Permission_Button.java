package com.example.demo.entity;

import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Table(name = "permission_button")
public class Permission_Button {
	private int id;
	private int permission_id;
	private int button_id;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPermission_id() {
		return permission_id;
	}
	public void setPermission_id(int permission_id) {
		this.permission_id = permission_id;
	}
	public int getButton_id() {
		return button_id;
	}
	public void setButton_id(int button_id) {
		this.button_id = button_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Permission_Button [id=" + id + ", permission_id=" + permission_id + ", button_id=" + button_id
				+ ", status=" + status + "]";
	}
	
}
