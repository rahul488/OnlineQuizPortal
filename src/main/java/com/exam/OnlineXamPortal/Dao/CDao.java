package com.exam.OnlineXamPortal.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.OnlineXamPortal.Entity.CQuestion;

public interface CDao extends JpaRepository<CQuestion, Long>{

}
