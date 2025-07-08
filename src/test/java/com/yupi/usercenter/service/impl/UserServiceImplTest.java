package com.yupi.usercenter.service.impl;

import com.yupi.usercenter.entity.UserRegisterDTO;
import com.yupi.usercenter.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {
	@Resource
	private UserService userService;

	@Test
	void userRegisterTest(){
		String username = "yupi";
		String userAccount = "yup";
		String userPassword = "123456789";
		String checkPassword = "123456789";
		UserRegisterDTO dto = new UserRegisterDTO();
		dto.setUsername(username);
		dto.setUserAccount(userAccount);
		dto.setUserPassword(userPassword);
		dto.setCheckPassword(checkPassword);
		long result = userService.userRegister(dto);
		Assertions.assertEquals(-1, result);
		userAccount = "yupi";
		userPassword = "1234567";
		dto.setUserAccount(userAccount);
		dto.setUserPassword(userPassword);
		long result2 = userService.userRegister(dto);
		Assertions.assertEquals(-1, result2);
		userAccount = "yupi";
		userPassword = "123456789";
		dto.setUserAccount(userAccount);
		dto.setUserPassword(userPassword);
		long result3 = userService.userRegister(dto);
		Assertions.assertEquals(-1, result3);
		userAccount = "yupi!%$#%$";
		dto.setUserAccount(userAccount);
		long result4 = userService.userRegister(dto);
		Assertions.assertEquals(-1, result4);
	}

}