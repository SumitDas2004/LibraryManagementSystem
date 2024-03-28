package com.minorproject.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.minorproject.Enums.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transactions implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="transaction_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="cardId")
	private Card card;

	@ManyToOne
	@JoinColumn(name="bookId")
	private Book book;
	
	@Column(name="book_due_date")
	private LocalDateTime bookDueDate;
	
	@Column(name="is_issued")
	private Boolean isIssued;
	
	@Column(name="is_returned")
	private Boolean isReturned;
	
	@Column(name="fineAmount")
	private Integer fineAmount;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
	
	@Column(name="created_on")
	@CreationTimestamp
	private LocalDateTime createdOn;
	
	@Column(name="updated_on")
	@UpdateTimestamp
	private LocalDateTime updatedOn;
}
