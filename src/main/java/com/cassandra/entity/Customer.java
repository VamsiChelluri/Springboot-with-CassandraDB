package com.cassandra.entity;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "customer")
@Getter
@Setter
public class Customer {

	@PrimaryKey
	private UUID id;
	private String name;
	private String address;
	private String email;
	
}
