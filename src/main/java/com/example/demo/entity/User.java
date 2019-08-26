package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Component
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String web_username;
	private String name;
	private String password;
	private String salt;
	private String openid;
	private String nickname;
	private String address;
	private String gender;
	private String birthday;
	private String mobile;
	private String viplevel; // 会员级别id
	private Date follow_time; // 关注时间
	private String headimgurl;
	private Integer page;
	private String plateno;
	private String balance;
	private String level;// 会员级别
	private String price;
	private String pass; // 操作密码
	private int status;
	// @Transient表示该属性并非一个到数据库表的字段的映射,ORM框架将忽略该属性. 如果一个属性并非数据库表的字段映射,就务必将其标示为@Transient,否则,ORM框架默认其注解为@Basic
	@Transient
	private String email;	
	@Transient
	private List<Role> role;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getWeb_username() {
		return web_username;
	}

	public void setWeb_username(String web_username) {
		this.web_username = web_username;
	}

	@NotEmpty(message="用户名不能为空")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Size(min=5,max=10,message="密码的长度应该在5和10之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getViplevel() {
		return viplevel;
	}

	public void setViplevel(String viplevel) {
		this.viplevel = viplevel;
	}

	public Date getFollow_time() {
		return follow_time;
	}

	public void setFollow_time(Date follow_time) {
		this.follow_time = follow_time;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getPlateno() {
		return plateno;
	}

	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	@Email(message="邮箱的格式不正确")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", web_username=" + web_username + ", name=" + name + ", password=" + password
				+ ", salt=" + salt + ", openid=" + openid + ", nickname=" + nickname + ", address=" + address
				+ ", gender=" + gender + ", birthday=" + birthday + ", mobile=" + mobile + ", viplevel=" + viplevel
				+ ", follow_time=" + follow_time + ", headimgurl=" + headimgurl + ", page=" + page + ", plateno="
				+ plateno + ", balance=" + balance + ", level=" + level + ", price=" + price + ", pass=" + pass
				+ ", status=" + status + ", email=" + email + ", role=" + role + "]";
	}


	
}