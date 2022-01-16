package com.ylh.jwt;

import com.ylh.oauth2.Oauth2Application;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest(classes = Oauth2Application.class)
public class JwtApplicationTests {

	@Value("${ylh.jwt.ID}")
	private String ID;

	@Value("${ylh.jwt.subject}")
	private String subject;

	@Value("${ylh.jwt.secretKey}")
	private String secretKey;

	@Value("${ylh.jwt.exp}")
	private long date;

	@Value("${ylh.jwt.claim.key}")
	private String key;

	@Value("${ylh.jwt.claim.value}")
	private String value;

	protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void createJwt() {
		long exp = System.currentTimeMillis() + (date * 60 * 1000);

		JwtBuilder jwtBuilder = Jwts.builder()
				// 唯一ID
				.setId(ID)
				// 接受的用户
				.setSubject(subject)
				// 签发时间
				.setIssuedAt(new Date())
				// 签发手段 ：加密方式、盐值
				.signWith(SignatureAlgorithm.HS256, secretKey)
				// 失效时间
				.setExpiration(new Date(exp))
				// 自定义参数 key: value形式
				.claim(key, value);

		String token = jwtBuilder.compact();
		System.out.println(token);
		// 解析
		parseToken(token);

		String[] tokens = token.split("\\.");
		System.out.println(Base64Codec.BASE64.decodeToString(tokens[0]));
		System.out.println(Base64Codec.BASE64.decodeToString(tokens[1]));
		// 乱码
		System.out.println(Base64Codec.BASE64.decodeToString(tokens[2]));
	}

	/**
	 * 解析JWT
	 */
	public void parseToken(String token){
		// 解析出来的对象
		Claims claims = (Claims) Jwts.parser()
				.setSigningKey(secretKey)
				.parse(token)
				.getBody();

		System.out.println("唯一ID: " + claims.getId()
				+ "\t接受用户: " + claims.getSubject()
				+ "\t签发时间：" + simpleDateFormat.format(claims.getIssuedAt())
				+ "\t过期时间: " + simpleDateFormat.format(claims.getExpiration())
				+ "\tclaim自定义参数：" + claims.get(key));
		System.out.println();
	}

}
