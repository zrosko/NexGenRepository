package com.rbc.nexgen.iipm.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbc.nexgen.iipm.service.dao.entity.ApplicationEntity;

public interface ApplicationJPA extends JpaRepository<ApplicationEntity, Integer> {
}
