package com.minorproject.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UpdateBookRequestModel implements Serializable{
    private String name;

	private String authorName;
	
	private Integer numberOfPages;
	
	private String language;

    private Boolean available;
	
	private String genre;
	
	private String ISBN;
	
	private LocalDateTime publishedDate;
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public UpdateBookRequestModel(String name, String authorName, Integer numberOfPages, String language, Boolean available,
            String genre, String iSBN, LocalDateTime publishedDate) {
        this.name = name;
        this.authorName = authorName;
        this.numberOfPages = numberOfPages;
        this.language = language;
        this.available = available;
        this.genre = genre;
        ISBN = iSBN;
        this.publishedDate = publishedDate;
    }

    public UpdateBookRequestModel() {
        super();
    }

    
}
