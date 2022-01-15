package com.ylh.oauth2.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author 云裂痕
 * @email 1046762075@qq.com
 * @date 2022-01-15 17:51:24
 */
public class User implements UserDetails {

	public User(String username, String password, List<GrantedAuthority> authoritys) {
		this.username = username;
		this.password = password;
		this.authoritys = authoritys;
	}

	private String username;

	private String password;

	private List<GrantedAuthority> authoritys;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<GrantedAuthority> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(List<GrantedAuthority> authoritys) {
		this.authoritys = authoritys;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
