package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.entity.User;
import com.yupi.usercenter.entity.UserLoginDTO;
import com.yupi.usercenter.entity.UserRegisterDTO;
import jakarta.servlet.http.HttpServletRequest;


/**
* @author Administrator
* @description 针对表【user】的数据库操作Service
* @createDate 2025-07-07 16:17:13
*/
public interface UserService extends IService<User> {
	long userRegister(UserRegisterDTO dto);
	User userLogin(UserLoginDTO dto, HttpServletRequest request);
}
