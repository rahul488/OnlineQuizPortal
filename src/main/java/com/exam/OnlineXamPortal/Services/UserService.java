package com.exam.OnlineXamPortal.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exam.OnlineXamPortal.Dao.UserDao;
import com.exam.OnlineXamPortal.Entity.User;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		User user=userDao.findByUserName(userName);
		
		if(user == null)
			throw new UsernameNotFoundException("user not found");
		
		return new UserPrinciple(user);
	}
	
	

}
