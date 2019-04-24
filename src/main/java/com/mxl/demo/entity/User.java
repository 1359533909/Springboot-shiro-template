package com.mxl.demo.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
	private int id;
	private String username;
	private String password;
	private String perms;
}
