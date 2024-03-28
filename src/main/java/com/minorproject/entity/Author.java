package com.minorproject.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Authors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="author_id")
	private Integer id;
	
	@Column(name="name", unique = true)
	private String name;
	
	@Column(name="age")
	private Integer age;
	
	@Column(name="email", unique = true)
	private String email;
	
	@Column(name="country")
	private String country;

}
