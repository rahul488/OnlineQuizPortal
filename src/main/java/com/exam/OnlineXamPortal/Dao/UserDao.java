package com.exam.OnlineXamPortal.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exam.OnlineXamPortal.Entity.User;

public interface UserDao extends JpaRepository<User, Integer>{
	@Query("select u from User u where u.userName =:userName")
	User findByUserName(@Param("userName")String userName);

}
