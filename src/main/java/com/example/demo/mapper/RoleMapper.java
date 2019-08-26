package com.example.demo.mapper;

import java.util.Set;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;

public interface RoleMapper{

	Role selectRolesByUserId(UserRole userRole);
	
	Set<UserRole> selectRolesAndUserByUserId(User user);
	
	int insertRoleByUser(UserRole userRole);

}
