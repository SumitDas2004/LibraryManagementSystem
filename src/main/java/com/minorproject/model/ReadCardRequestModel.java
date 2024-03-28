package com.minorproject.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.minorproject.Enums.CardStatus;
import com.minorproject.entity.Student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadCardRequestModel implements Serializable{
	private Integer id;
	
	private CardStatus status;
	
	private LocalDateTime updatedOn;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime validThrough;
	
	private Student student;

		
}
