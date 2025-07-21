package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.entity.UserLoginDTO;
import com.yupi.usercenter.entity.UserRegisterDTO;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.utils.PasswordEncryptor;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.yupi.usercenter.entity.User;
import com.yupi.usercenter.mapper.UserMapper;

import static com.yupi.usercenter.content.UserContent.USER_LOGIN_STATE;


@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
		implements UserService {

	@Resource
	private UserMapper userMapper;


	@Override
	public long userRegister(UserRegisterDTO dto) {
		// 参数是否为空
		if (StringUtils.isAllBlank(dto.getUsername(), dto.getUserAccount(), dto.getUserPassword(), dto.getCheckPassword())) {
			return -1;
		}
		// 账户不能小于4位
		if (dto.getUserAccount().length() < 4) {
			return -1;
		}
		// 密码不能小于8位
		if (dto.getUserPassword().length() < 8 || dto.getCheckPassword().length() < 8) {
			return -1;
		}
		// 账户不能包含特殊字符
		boolean matches = dto.getUserAccount().matches("^[a-zA-Z0-9]+$");
		if (!matches) {
			return -1;
		}
		// 密码和校验密码相同
		if (!dto.getUserPassword().equals(dto.getCheckPassword())) {
			return -1;
		}
		// 账户不能重复
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userAccount", dto.getUserAccount());
		long count = userMapper.selectCount(queryWrapper);
		if (count > 0) {
			return -1;
		}

		// 加密
		// 获取明文密码
		String plainPassword = dto.getUserPassword();
		// 生成盐值
		String salt = PasswordEncryptor.generateSalt();
		// 对密码进行加密
		String encryptedPassword = PasswordEncryptor.encryptPassword(plainPassword, salt);

		// 插入数据
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setUserAccount(dto.getUserAccount());
		user.setUserPassword(encryptedPassword);
		user.setSalt(salt);
		boolean saveResult = this.save(user);
		if (!saveResult) {
			return -1;
		}
		return user.getId();
	}

	@Override
	public User userLogin(UserLoginDTO dto, HttpServletRequest request) {
		// 参数是否为空
		if (StringUtils.isAllBlank(dto.getUserAccount(), dto.getUserPassword())) {
			return null;
		}
		// 账户不能小于4位
		if (dto.getUserAccount().length() < 4) {
			return null;
		}
		// 账户不能包含特殊字符
		boolean matches = dto.getUserAccount().matches("^[a-zA-Z0-9]+$");
		if (!matches) {
			return null;
		}
		// 账户不能重复
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userAccount", dto.getUserAccount());
		User salt = userMapper.selectOne(queryWrapper);
		if (salt == null) {
			log.info("用户不存在");
			return null;
		}

		// 对比加密密码
		// 获取明文密码
		String plainPassword = dto.getUserPassword();
		// 对密码进行比较
		String encryptedPassword = PasswordEncryptor.encryptPassword(plainPassword, salt.getSalt());
		queryWrapper.eq("userPassword", encryptedPassword);
		User user = userMapper.selectOne(queryWrapper);
		if (user == null) {
			log.info("密码错误");
			return null;
		}
		// 脱敏
		User desensitizedUserInfoAfterLogin = getSafetyUser(user);
		// 登录成功，记录用户信息
		request.getSession().setAttribute(USER_LOGIN_STATE, desensitizedUserInfoAfterLogin);
		return desensitizedUserInfoAfterLogin;
	}

	@Override
	public User getSafetyUser(User user) {
		User desensitizedUserInfoAfterLogin = new User();
		desensitizedUserInfoAfterLogin.setId(user.getId());
		desensitizedUserInfoAfterLogin.setUsername(user.getUsername());
		desensitizedUserInfoAfterLogin.setUserAccount(user.getUserAccount());
		desensitizedUserInfoAfterLogin.setAvatarUrl(user.getAvatarUrl());
		desensitizedUserInfoAfterLogin.setGender(user.getGender());
		desensitizedUserInfoAfterLogin.setPhone(user.getPhone());
		desensitizedUserInfoAfterLogin.setEmail(user.getEmail());
		desensitizedUserInfoAfterLogin.setRole(user.getRole());
		desensitizedUserInfoAfterLogin.setUserStatus(user.getUserStatus());
		desensitizedUserInfoAfterLogin.setCreateTime(user.getCreateTime());
		return desensitizedUserInfoAfterLogin;
	}
}




