package com.yupi.usercenter.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDTO implements Serializable {
	private String username;
	private String userAccount;
	private String userPassword;
	private String checkPassword;
}
