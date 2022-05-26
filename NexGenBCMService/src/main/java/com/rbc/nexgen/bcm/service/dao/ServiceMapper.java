package com.rbc.nexgen.bcm.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rbc.nexgen.bcm.service.dao.entity.ServiceEntity;

public class ServiceMapper implements RowMapper<ServiceEntity> {
	public ServiceEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		ServiceEntity service = new ServiceEntity();
		service.setId(rs.getInt("id"));
		service.setName(rs.getString("name"));
		service.setRto(rs.getInt("rto"));
		service.setRpo(rs.getInt("rpo"));
		service.setType(rs.getString("type"));
		return service;
	}

}
