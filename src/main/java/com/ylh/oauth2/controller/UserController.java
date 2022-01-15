package com.ylh.oauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 云裂痕
 * @email 1046762075@qq.com
 * @date 2022-01-15 18:38:18
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping("/getCurrentUser")
	public Object getCurrentUser(Authentication auth){
		return auth.getPrincipal();
	}
}
