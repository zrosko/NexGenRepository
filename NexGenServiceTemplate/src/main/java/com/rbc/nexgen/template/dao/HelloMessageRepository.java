package com.rbc.nexgen.template.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rbc.nexgen.template.model.HelloMessage;

@Repository
public interface HelloMessageRepository extends CrudRepository<HelloMessage, Long> {

	HelloMessage findById(int id);

}
