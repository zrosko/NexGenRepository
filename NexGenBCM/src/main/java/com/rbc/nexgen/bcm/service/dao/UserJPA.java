package com.rbc.nexgen.bcm.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbc.nexgen.bcm.service.dao.entity.UserEntity;

public interface UserJPA extends JpaRepository<UserEntity, Integer> {
}
