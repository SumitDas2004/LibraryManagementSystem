package com.minorproject.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="book_id")
	private Integer id;
	
	@Column(name="book_name", unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name="atuhor_id")
	private Author author;
	
	@Column(name="number_of_pages")
	private Integer numberOfPages;
	
	@Column(name="language")
	private String language;
	
	@Column(name="available")
	private Boolean available;
	
	@Column(name="genre")
	private String genre;
	
	@Column(name="isbn", unique = true)
	private String ISBN;
	
	@Column(name="published_date")
	private LocalDateTime publishedDate;	

}
