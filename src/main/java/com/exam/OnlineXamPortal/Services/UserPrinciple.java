package com.exam.OnlineXamPortal.Services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.exam.OnlineXamPortal.Entity.User;

public class UserPrinciple implements UserDetails{
	
	String username=null;
	String password=null;
	List<GrantedAuthority> authority;
	
	public UserPrinciple(User user) {
		this.username=user.getUserName();
		this.password=user.getPassword();
		this.authority=Arrays.stream(user.getRole().split(",")).
					   map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authority;
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
