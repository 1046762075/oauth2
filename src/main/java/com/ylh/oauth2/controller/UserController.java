package com.ylh.oauth2.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author 云裂痕
 * @email 1046762075@qq.com
 * @date 2022-01-15 18:38:18
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Value("${ylh.jwt.signKey}")
	private String signKey;

	@GetMapping("/getCurrentUser")
	public Object getCurrentUser(Authentication auth){
		return auth.getPrincipal();
	}

	@RequestMapping("/parseToken")
	public Object parseToken(HttpServletRequest request){
		String header = request.getHeader("Authorization");
		String token = header.substring(header.lastIndexOf("bearer") + 7);

		// 解析
		return Jwts.parser()
				.setSigningKey(signKey.getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token).getBody();
	}
}
