package com.ylh.oauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 转换器
 * @author 云裂痕
 * @email 1046762075@qq.com
 * @date 2022-01-15 23:51:46
 */
@Configuration
public class JwtTokenStoreConfig {


	@Value("${ylh.jwt.signKey}")
	private String signKey;

	@Bean
	public TokenStore jwtTokenStore(){
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	/**
	 * Jwt转换器
	 * 将auth2生成的密钥转换成jwt
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(){
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		// jwt密钥
		jwtAccessTokenConverter.setSigningKey(signKey);
		return jwtAccessTokenConverter;
	}
}
