package com.example.demo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;

public interface UserService {

	User selectUserByWebUserName(@Valid User user);

	User selectUserInfoById(User users);

	int insertUserInfo(@Valid User user);

	Map<String, Object> userLogin(@Valid User user ,HttpSession session);

	List<User> selectUserList();




}
