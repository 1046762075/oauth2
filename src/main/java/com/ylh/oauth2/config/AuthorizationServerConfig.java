package com.ylh.oauth2.config;

import com.ylh.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权服务器
 * @author 云裂痕
 * @email 1046762075@qq.com
 * @date 2022-01-15 18:26:29
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenStore redisTokenStore;


	/**
	 * 密码模式
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer auth) throws Exception{
		auth.authenticationManager(authenticationManager).userDetailsService(userService)
				// token存储的位置
				.tokenStore(redisTokenStore);
	}

	//	http://localhost:8080/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://www.baidu.com&scope=all
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				// 客户端id
				.withClient("client")
				// 密钥
				.secret(passwordEncoder.encode("123456"))
				// 重定向地址
				.redirectUris("http://www.baidu.com")
				// 授权范围
				.scopes("all")
				/**
				 * 授权类型 [允许多种]
				 * authorization_code：授权码模式
				 * password：密码模式
				 */
				.authorizedGrantTypes("authorization_code", "password");
	}
}
