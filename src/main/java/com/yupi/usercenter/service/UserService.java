package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.entity.User;
import com.yupi.usercenter.entity.UserLoginDTO;
import com.yupi.usercenter.entity.UserRegisterDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
	/**
	 * 用户注册
	 * @param dto 注册信息
	 * @return 注册结果
	 */
	long userRegister(UserRegisterDTO dto);

	/**
	 * 用户登录
	 * @param dto 登录信息
	 * @param request 请求
	 * @return 用户信息
	 */
	User userLogin(UserLoginDTO dto, HttpServletRequest request);

	/**
	 * 用户脱敏
	 * @param originUser 原始用户信息
	 * @return 脱敏后的用户信息
	 */
	User getSafetyUser(User originUser);

	/**
	 * 用户退出登录
	 * @param request 请求
	 */
	void logout(HttpServletRequest request);
}
