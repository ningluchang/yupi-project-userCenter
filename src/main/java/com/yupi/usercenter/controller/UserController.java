package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
	public long userRegister(@RequestBody UserRegisterDTO dto) {
		return userService.userRegister(dto);
	}

	@PostMapping("/login")
	public User userLogin(@RequestBody UserLoginDTO dto, HttpServletRequest request) {
		return userService.userLogin(dto, request);
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
	public boolean deleteUser(@RequestBody Long id, HttpServletRequest request) {
		if (isAdmin(request)) {
			return false;
		}
		if (id < 0) {
			return false;
		}
		return userService.removeById(id);
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
