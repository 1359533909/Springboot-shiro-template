package com.mxl.demo.mapper;

import com.mxl.demo.entity.User;

public interface UserMapper {
	public User findUserByName(String name);
	public User findUserById(int id);
}
