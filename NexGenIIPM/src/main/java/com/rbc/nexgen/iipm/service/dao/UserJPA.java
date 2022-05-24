package com.rbc.nexgen.iipm.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbc.nexgen.iipm.service.dao.entity.UserEntity;

public interface UserJPA extends JpaRepository<UserEntity, Integer> {
}
