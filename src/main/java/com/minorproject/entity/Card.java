package com.minorproject.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minorproject.Enums.CardStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="card_id")
	private Integer id;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private CardStatus status;
	
	@Column(name="updated_on")
	@UpdateTimestamp
	private LocalDateTime updatedOn;
	
	@Column(name="created_on")
	@CreationTimestamp
	private LocalDateTime createdOn;
	
	@Column(name="valid_through")
	private LocalDateTime validThrough;
	
	@OneToOne
	@JoinColumn(name="student_id")
	@JsonIgnore
	private Student student;
}
