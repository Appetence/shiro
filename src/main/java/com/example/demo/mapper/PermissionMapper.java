package com.example.demo.mapper;

import java.util.Set;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;

public interface PermissionMapper {

	Set<Permission> selectPermissionByUser(Role r);

	Set<String> selectPermissionsByUserId(Role r);

}
