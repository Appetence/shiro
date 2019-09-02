package com.example.demo.service;

import java.util.Set;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;

public interface RoleService {


	Set<Role> selectRolesByUserId(User user);

	int insertRoleByUser(UserRole userRole);


}
