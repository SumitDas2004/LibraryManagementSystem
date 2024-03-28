package com.minorproject.Repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minorproject.entity.Author;
import com.minorproject.entity.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b WHERE ISBN=?1")
    public Book getByISBN(String isbn);

    @Query("SELECT b FROM Book b WHERE name=?1 AND author=?2")
    public Book getByNameAndAuthor(String name, Author authhor);

    @Query("SELECT b FROM Book b WHERE genre=?1")
    public ArrayList<Book> getBookByGenre(String genre);
}
