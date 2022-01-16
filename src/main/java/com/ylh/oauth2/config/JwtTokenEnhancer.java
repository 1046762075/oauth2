package com.ylh.oauth2.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展Jwt
 * @author 云裂痕
 * @email 1046762075@qq.com
 * @date 2022-01-16 00:18:37
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
		Map<String, Object> map = new HashMap<>();
		map.put("enhancer", "enhancer info");
		((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(map);
		return oAuth2AccessToken;
	}
}
