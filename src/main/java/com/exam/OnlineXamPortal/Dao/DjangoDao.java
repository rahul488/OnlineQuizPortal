package com.exam.OnlineXamPortal.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.OnlineXamPortal.Entity.DjangoQuestion;

public interface DjangoDao extends JpaRepository<DjangoQuestion, Long> {

}
