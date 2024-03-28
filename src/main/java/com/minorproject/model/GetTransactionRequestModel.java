package com.minorproject.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.minorproject.Enums.TransactionStatus;

import com.minorproject.entity.Book;
import com.minorproject.entity.Card;

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
public class GetTransactionRequestModel implements Serializable{
    private Integer id;
	
	private Card card;

    private Book book;
	
	private LocalDateTime bookDueDate;
	
	private Boolean isIssued;
	
	private Boolean isReturned;
	
	private Integer fineAmount;
	
	private TransactionStatus status;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

}
