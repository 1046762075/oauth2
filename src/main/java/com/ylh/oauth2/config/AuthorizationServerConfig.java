package com.ylh.oauth2.config;

import com.ylh.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务器
 * @author 云裂痕
 * @email 1046762075@qq.com
 * @date 2022-01-15 18:26:29
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${ylh.jwt.exp}")
	private int exp;

	@Value("${ylh.jwt.refreshToken}")
	private int refreshToken;

	@Value("${oauth2.secret}")
	private String secret;

	@Value("${oauth2.client}")
	private String client;

	@Value("${oauth2.redirectUris}")
	private String[] redirectUris;

	@Value("${oauth2.scopes}")
	private String scopes;

	@Value("${oauth2.authorizedGrantTypes}")
	private String[] authorizedGrantTypes;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	/**
	 * redis存储方式 [配合oauth2]
	 */
//	@Autowired
//private TokenStore redisTokenStore;

	/**
	 * jwt方式
	 */
	@Autowired
	@Qualifier("jwtTokenStore")
	private TokenStore jwtTokenStore;

	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Autowired
	private JwtTokenEnhancer jwtTokenEnhancer;

	/**
	 * 密码模式
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer auth) {
		// 设置增强内容
		TokenEnhancerChain chain = new TokenEnhancerChain();
		/**
		 * jwtAccessTokenConverter 是必须的
		 * jwtTokenEnhancer 是增强内容
		 */
		List<TokenEnhancer> delegates = new ArrayList<>();
		delegates.add(jwtTokenEnhancer);
		delegates.add(jwtAccessTokenConverter);
		chain.setTokenEnhancers(delegates);

		auth.authenticationManager(authenticationManager).userDetailsService(userService)
				// token存储的位置
				.tokenStore(jwtTokenStore)
				// token转换 [jwt特有]
				.accessTokenConverter(jwtAccessTokenConverter)
				.tokenEnhancer(chain);
	}

	//	http://localhost:8080/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://www.baidu.com&scope=all
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				// 客户端id
				.withClient(client)
				// 密钥 [与客户端的密钥一致]
				.secret(passwordEncoder.encode(secret))
				// 重定向地址
				.redirectUris(redirectUris)
				// 授权范围
				.scopes(scopes)
				// 有效期
				.accessTokenValiditySeconds(exp * 60)
				// 刷新令牌过期时间
				.refreshTokenValiditySeconds(refreshToken)
				// 单点登录自动授权
				.autoApprove(true)
				/**
				 * 授权类型 [允许多种]
				 * authorization_code：授权码模式
				 * password：密码模式
				 * refresh_token: 刷新令牌
				 */
				.authorizedGrantTypes(authorizedGrantTypes);
	}

	/**
	 * 配置单点登录
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
		// 获取密钥必须要身份认证
		security.tokenKeyAccess("isAuthenticated()");
	}
}
