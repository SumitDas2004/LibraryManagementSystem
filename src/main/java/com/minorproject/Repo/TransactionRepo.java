package com.minorproject.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minorproject.entity.Book;
import com.minorproject.entity.Card;
import com.minorproject.entity.Transactions;

@Repository
public interface TransactionRepo extends JpaRepository<Transactions, Integer>{

    @Query("SELECT t FROM Transactions t WHERE book=?1 AND card=?2")
    public List<Transactions> getTransactionsUsingBookAndCard(Book book, Card card);

    @Query("SELECT t FROM Transactions t WHERE book=?1")
    public List<Transactions> getUsingBook(Book book);
}