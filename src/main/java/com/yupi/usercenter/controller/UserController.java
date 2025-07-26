package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.entity.User;
import com.yupi.usercenter.entity.UserLoginDTO;
import com.yupi.usercenter.entity.UserRegisterDTO;
import com.yupi.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yupi.usercenter.content.UserContent.ADMIN_ROLE;
import static com.yupi.usercenter.content.UserContent.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@PostMapping("/register")
	public BaseResponse<Long> userRegister(@RequestBody UserRegisterDTO dto) {
		long l = userService.userRegister(dto);
		return ResultUtils.success(l);
	}

	@PostMapping("/login")
	public BaseResponse<User> userLogin(@RequestBody UserLoginDTO dto, HttpServletRequest request) {
		User user = userService.userLogin(dto, request);
		return ResultUtils.success(user);
	}

	@GetMapping("/search")
	public List<User> searchUsers(@RequestParam String username, HttpServletRequest request) {
		if (isAdmin(request)) {
			return new ArrayList<>();
		}
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(username)) {
			queryWrapper.like("username", username);
		}
		List<User> userList = userService.list(queryWrapper);
		return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
	}

	@PostMapping("/delete")
	public BaseResponse<?> deleteUser(@RequestBody Long id, HttpServletRequest request) {
		if (isAdmin(request)) {
			return null;
		}
		if (id < 0) {
			return null;
		}
		Boolean b = userService.removeById(id);
		return ResultUtils.success(b);
	}

	private boolean isAdmin(HttpServletRequest request){
		// 管理员可查询
		Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
		User loginUser = (User) userObject;
		return loginUser == null || loginUser.getRole() != ADMIN_ROLE;
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		userService.logout(request);
		return ResponseEntity.ok().build();
	}
}
