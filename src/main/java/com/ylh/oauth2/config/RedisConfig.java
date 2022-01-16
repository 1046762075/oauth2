package com.ylh.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * jwt之后取消这个配置
 * token存储到redis
 * @author 云裂痕
 * @email 1046762075@qq.com
 * @date 2022-01-15 21:30:57
 */
//@Configuration
public class RedisConfig {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Bean
	public TokenStore redisTokenStore(){
		return new RedisTokenStore(redisConnectionFactory);
	}
}
