package com.example.demo.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
@Table(name = "permission")
public class Permission {
	 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String desc_;

	private String url;
	
	private String level;
	
	private Long pid;
	
	private String menu_type;
	@Transient
	private List<Permission> permissions;
	@Transient
	private List<Button> buttons;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc_() {
		return desc_;
	}

	public void setDesc_(String desc_) {
		this.desc_ = desc_;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getMenu_type() {
		return menu_type;
	}

	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", desc_=" + desc_ + ", url=" + url + ", level=" + level
				+ ", pid=" + pid + ", menu_type=" + menu_type + ", permissions=" + permissions + "]";
	}
	
	
}