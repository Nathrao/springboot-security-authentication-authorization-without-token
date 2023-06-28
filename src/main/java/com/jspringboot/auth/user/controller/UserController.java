package com.jspringboot.auth.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jspringboot.auth.user.entity.UserEntity;
import com.jspringboot.auth.user.repository.UserRepository;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@PostMapping("/register")
	public String addProduct(@RequestBody UserEntity entity) {
		try {
			entity.setPassword(encoder.encode(entity.getPassword()));
			userRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "User added successfully";
	}
}
