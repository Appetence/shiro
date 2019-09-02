package com.example.demo.mapper;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.entity.User;

public interface UserMapper  {


	User selectUserByWebUserName(@Valid User user);

	User selectUserInfoById(User users);

	int insertUserInfo(@Valid User user);

	List<User> selectUserList();

}
