package com.minorproject.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_id")
	private Integer id;
	
	@Column(name="age")
	private Integer age;
	
	@Column(name="name")
	private String name;
	
	@Column(name="country")
	private String country;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone", unique = true, length = 10)
	private String phoneNumber;

	@OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
	@JsonIgnore
	private Card card;
	
	@Column(name="created_on")
	@CreationTimestamp
	private LocalDateTime createdOn;
	
	@Column(name="updated_on")
	@UpdateTimestamp
	private LocalDateTime updatedOn;
}
