package org.ylh.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 云裂痕
 * @email 1046762075@qq.com
 * @date 2022-01-16 17:29:11
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping("/getCurrentUser")
	public Object getCurrentUser(Authentication auth){
		return auth;
	}

}
