package com.example.demo.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Component
@Table(name = "user_role")
public class UserRole {
	 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer uid;

	private Integer rid;
	

	public UserRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserRole(Integer uid, Integer rid) {
		super();
		this.uid = uid;
		this.rid = rid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", uid=" + uid + ", rid=" + rid + "]";
	}
	
	
}