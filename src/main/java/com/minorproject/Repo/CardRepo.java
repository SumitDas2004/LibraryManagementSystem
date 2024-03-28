package com.minorproject.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minorproject.entity.Card;

@Repository
public interface CardRepo extends JpaRepository<Card, Integer>{
    
}