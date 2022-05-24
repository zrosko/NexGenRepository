package com.rbc.nexgen.bcm.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbc.nexgen.bcm.service.dao.entity.ApplicationEntity;

public interface ApplicationJPA extends JpaRepository<ApplicationEntity, Integer> {
}
