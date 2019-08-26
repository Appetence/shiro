package com.example.demo.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Component
@Table(name = "role_permission")
public class RolePermission {
	 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Long rid;

	private Long pid;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "RolePermission [id=" + id + ", rid=" + rid + ", pid=" + pid + "]";
	}

	
}