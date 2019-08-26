package com.example.demo.service;

import java.util.List;
import java.util.Set;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;

public interface PermissionService {



	Set<Permission> selectPermissionByUser(Role r);

	Set<String> selectPermissionsByUserId(Role r);



}
