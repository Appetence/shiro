package com.example.demo.service.Impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.mapper.ButtonMapper;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.RoleService;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	ButtonMapper buttonMapper;
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	PermissionMapper permissionMapper;
	@Autowired
	UserMapper userMapper;
	@Override
	public Set<Role> selectRolesByUserId(User user) {
		Set<Role> roles = new HashSet<Role>();
		Set<UserRole> uRoles = roleMapper.selectRolesAndUserByUserId(user);
		if(uRoles!=null&&uRoles.size()>0){
			for(UserRole ur :uRoles){
				Role role = roleMapper.selectRolesByUserId(ur);
				roles.add(role);
			}
		}
		return roles;
	}
	@Override
	public int insertRoleByUser(UserRole userRole) {
		
		return roleMapper.insertRoleByUser(userRole);
	}

}
