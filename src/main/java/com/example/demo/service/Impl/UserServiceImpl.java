package com.example.demo.service.Impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.mapper.ButtonMapper;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.ButtonService;
import com.example.demo.service.PermissionService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.shiro.UserToken;
import com.example.demo.util.HashSatlUtil;
@Service
@Transactional
public class UserServiceImpl implements UserService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ButtonService buttonService;
	@Autowired
	RoleService roleService;
	@Autowired
	PermissionService permissionService;
	@Autowired
	UserService userService;
	@Autowired
	ButtonMapper buttonMapper;
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	PermissionMapper permissionMapper;
	@Autowired
	UserMapper userMapper;
	@Override
	public User selectUserByWebUserName(@Valid User user) {
		//调方法
		return userMapper.selectUserByWebUserName(user);
	}
	@Override
	public User selectUserInfoById(User users) {
		//调方法
		return userMapper.selectUserInfoById(users);
	}
	@Override
	public int insertUserInfo(@Valid User user) {
		String method = "md5";
		String pass = user.getPassword();
		int times = 2;		
		try{
			Map<String, Object> map = HashSatlUtil.HashSaltUtil(method, pass, times);
			String salt = (String) map.get("salt");
			String encodedPassword = (String)map.get("encodedPassword");
			user.setSalt(salt);
			user.setPassword(encodedPassword);
			int result = userMapper.insertUserInfo(user);
			//用户注册成功之后，默认角色5为普通用户
			UserRole userRole = new UserRole(user.getId(),5);
			//设置用户角色权限信息
			int resultRole = roleService.insertRoleByUser(userRole);
			if(result>0 && resultRole>0){
				logger.info("用户注册成功");
				return 1;
			}
		}catch (Exception e) {
			logger.error("密码散列错误",e.getMessage());
		}
		
		return 0;
	}
	@Override
	public Map<String, Object> userLogin(@Valid User user,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Subject subject = SecurityUtils.getSubject();//获取subject
    	//传入用户名密码
    	UserToken token=new UserToken(user.getWeb_username(), user.getPassword(),"Web");
    	try{
    		subject.login(token);
    		String userMobile=(String) SecurityUtils.getSubject().getPrincipal();
    		user.setMobile(userMobile);
    		//查询用户信息
    		User userInfo = this.selectUserByWebUserName(user);
    		session.setAttribute("userInfo", userInfo);
    		//获取用户角色信息
    		Set<Role> roles = roleService.selectRolesByUserId(userInfo);
    		if(roles!=null&&roles.size()>0){
    			//获取权限信息
        		Set<Permission> permissionsSet = new HashSet<Permission>();
        		for(Role r : roles){
        			Set<Permission> permissions = permissionService.selectPermissionByUser(r);
        			//查询到的属性放到set中
        			permissionsSet.addAll(permissions);
        		}
        		session.setAttribute("permissions", permissionsSet);
        		session.setAttribute("roles", roles);
        		map.put("roleList", roles);
    			map.put("roleSize", roles.size());
    			map.put("success", true);
    		}else{
    			map.put("success", true);
    			map.put("roleList", roles);
    			map.put("roleSize", 0);
    		}
    		
    		
    	}catch (UnknownAccountException uae){  //账号错误
			map.put("success", false);
			map.put("errorInfo", "账号错误");
			logger.error("web端认证   账号错误,e:{}"+uae.getMessage());
        }catch (IncorrectCredentialsException e) {//密码错误
			map.put("success", false);
			map.put("errorInfo", "密码错误");
        	logger.error("web端认证  密码错误");
		} catch (LockedAccountException e) {
			map.put("success", false);
			map.put("errorInfo", "登录失败，该用户已被冻结");
			logger.error("web端认证   登录失败，该用户已被冻结");
		} catch (ExcessiveAttemptsException e) {
			map.put("success", false);
			map.put("errorInfo", "登录失败，错误次数超过三次请稍后再试");
			logger.error("web端认证   登录失败，错误次数超过三次请稍后再试");
		}catch (AuthenticationException e) {//账号或密码错误
			map.put("success", false);
			map.put("errorInfo", "该用户不存在");
			logger.error("web端认证  该用户不存在");
		}catch (Exception e) {
    		e.printStackTrace();
			map.put("success", false);
			map.put("errorInfo", "用户名或者密码错误！");
			logger.error("web端认证   用户名或者密码错误！");
		}
    	return map;
	}
	@Override
	public List<User> selectUserList() {
		List<User> users = userMapper.selectUserList();
		return users;
	}

}
