package com.rbc.nexgen.bcm.service.dao.entity;

import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
//https://howtodoinjava.com/jpa/jpa-native-query-example-select/
@Entity(name="ServiceEntity")
@Table(name="bcm_service")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@NamedNativeQueries({
    @NamedNativeQuery(
        name  = "getAllServices",
        query = "SELECT id, name, type, rto, rpo FROM nexgenbcm.bcm_service ",
              resultClass=ServiceEntity.class
    ),
    @NamedNativeQuery(
        name  = "getAllServicesUpdated",
        query = "SELECT id, name, type, rto, rpo FROM nexgenbcm.bcm_service " +
              "where id between 2 and 3",
              resultClass=ServiceEntity.class
    )
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name="test", columns = @ColumnResult(name = "clt"))
})
public class ServiceEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotBlank(message="Name is mandatory")
	@Length(min=1, max=30, message="Name min is 1 and max is 30")
	private String name;
	private int rto;
	private int rpo;
	private String type;
}
