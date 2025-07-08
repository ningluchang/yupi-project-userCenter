package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.entity.UserRegisterDTO;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.utils.PasswordEncryptor;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.yupi.usercenter.entity.User;
import com.yupi.usercenter.mapper.UserMapper;

/**
 * @author Administrator
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2025-07-07 16:17:13
 */
@Service
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
}




