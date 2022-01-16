package org.ylh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/* 开启单点登录 */
@EnableOAuth2Sso
@SpringBootApplication
public class Client {

	// http://localhost:8088/user/getCurrentUser

	public static void main(String[] args) {
		SpringApplication.run(Client.class, args);
	}

}
