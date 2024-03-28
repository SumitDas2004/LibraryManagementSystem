package com.minorproject.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minorproject.entity.Author;


@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer>{  
    @Query("SELECT a FROM Author a WHERE email=?1")  
    public Author getByEmail(String email);

    @Query("SELECT a FROM Author a WHERE name=?1")  
    public Author getByName(String name);
}
