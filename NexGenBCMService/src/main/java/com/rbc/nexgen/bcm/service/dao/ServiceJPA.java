package com.rbc.nexgen.bcm.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.rbc.nexgen.bcm.service.dao.entity.ServiceEntity;

//@NoRepositoryBean
public interface ServiceJPA extends JpaRepository<ServiceEntity, Integer> {

	public List<ServiceEntity> getAllServicesUpdated();
	public List<ServiceEntity> getAllServices();

}
