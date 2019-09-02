package com.example.demo.service.Impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.mapper.ButtonMapper;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.PermissionService;
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	ButtonMapper buttonMapper;
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	PermissionMapper permissionMapper;
	@Autowired
	UserMapper userMapper;
	@Override
	public Set<Permission> selectPermissionByUser(Role r) {
		
		return permissionMapper.selectPermissionByUser(r);
	}
	@Override
	public Set<String> selectPermissionsByUserId(Role r) {
		
		return permissionMapper.selectPermissionsByUserId(r);
	}

}
