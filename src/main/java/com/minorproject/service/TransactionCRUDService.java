package com.minorproject.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.minorproject.Enums.TransactionStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.minorproject.Repo.TransactionRepo;
import com.minorproject.entity.Book;
import com.minorproject.entity.Card;
import com.minorproject.entity.Transactions;
import com.minorproject.exception.ForbiddenException;
import com.minorproject.model.CreateTransactionRequestModel;
import com.minorproject.model.GetTransactionRequestModel;

@Service
public class TransactionCRUDService {

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    BookCRUDService bookCRUDService;
    @Autowired
    CardCRUDService cardCRUDService;
    @Value("${book.issue.validity}")
    int bookvalidFor;

    public String issueBook(CreateTransactionRequestModel request) throws Exception {

        boolean isIssued = true;
        boolean isReturned = false;
        
        Card card = cardCRUDService.get(request.getCardId());
        Book book = bookCRUDService.getBookUsingID(request.getBookId());

        LocalDateTime curDate = LocalDateTime.now();

        // When card is not active
        if (isIssued && curDate.isAfter(ChronoLocalDateTime.from(card.getValidThrough()))) {
            Transactions transaction = Transactions.builder()
                    .card(card)
                    .book(book)
                    .isIssued(isIssued)
                    .isReturned(isReturned)
                    .fineAmount(0)
                    .status(TransactionStatus.FAILED)
                    .build();
            transactionRepo.save(transaction);

            throw new ForbiddenException("Unable to complete transaction: The card is not active");
        }
        // When book is not available
        if (isIssued && !book.getAvailable()) {
            Transactions transaction = Transactions.builder()
                    .card(card)
                    .book(book)
                    .isIssued(isIssued)
                    .isReturned(isReturned)
                    .fineAmount(0)
                    .status(TransactionStatus.FAILED)
                    .build();
            transactionRepo.save(transaction);
            throw new ForbiddenException("Unable to complete transaction: The book is not available.");
        }

        LocalDateTime bookDueDate = LocalDateTime.now().plusDays(bookvalidFor);
        
        Transactions transaction = Transactions.builder()
                .card(card)
                .book(book)
                .isIssued(isIssued)
                .isReturned(isReturned)
                .fineAmount(0)
                .status(TransactionStatus.SUCCESSFULL)
                .bookDueDate(bookDueDate)
                .build();

        transactionRepo.save(transaction);
        book.setAvailable(false);
        
        bookCRUDService.updateBook(book);

        return "Transaction Successfull.";
    }

    public String returnBook(CreateTransactionRequestModel request) throws Exception {

        boolean isIssued = false;
        boolean isReturned = true;

        Card card = cardCRUDService.get(request.getCardId());
        Book book = bookCRUDService.getBookUsingID(request.getBookId());

        LocalDateTime curDate = LocalDateTime.now();

        // When card is not active
        if (isIssued && curDate.isAfter(ChronoLocalDateTime.from(card.getValidThrough()))) {
            Transactions transaction = Transactions.builder()
                    .card(card)
                    .book(book)
                    .isIssued(isIssued)
                    .isReturned(isReturned)
                    .fineAmount(0)
                    .status(TransactionStatus.FAILED)
                    .build();
            transactionRepo.save(transaction);
            throw new ForbiddenException("Unable to complete transaction: The card is not active");
        }

        List<Transactions> t = transactionRepo.getTransactionsUsingBookAndCard(book, card);

        Collections.sort(t, (x, y) -> {
            return x.getCreatedOn().compareTo(y.getCreatedOn());
        });

        Transactions recentMost = t.get(t.size() - 1);

        // Makes sure that in the recent most transaction involving the book and card,
        // the book was issued.
        // The transaction fails otherwise, as book which was not issued can't be
        // returned.
        if (recentMost.getIsReturned()) {

            Transactions transaction = Transactions.builder()
                    .card(card)
                    .book(book)
                    .isIssued(isIssued)
                    .isReturned(isReturned)
                    .fineAmount(0)
                    .status(TransactionStatus.FAILED)
                    .build();

            transactionRepo.save(transaction);

            throw new ForbiddenException("Invalid Tranaction.");
        }

        int fineAmount = calculateFine(curDate, recentMost.getBookDueDate());
        Transactions transaction = Transactions.builder()
                .card(card)
                .book(book)
                .isIssued(isIssued)
                .isReturned(isReturned)
                .fineAmount(fineAmount)
                .status(TransactionStatus.SUCCESSFULL)
                .build();

        transactionRepo.save(transaction);
        book.setAvailable(true);
        bookCRUDService.updateBook(book);;

        return "Transaction successfull.";
    }

    public int calculateFine(LocalDateTime curDate, LocalDateTime dueDate) throws Exception {
        if (curDate.isBefore(dueDate))
            return 0;
        Duration difference = Duration.between(curDate, dueDate);
        return (int) difference.toDays() * 2;
    }

    @Cacheable(value="LibraryManagement_Transaction_Id", key="#id", unless="#result==null")
    public GetTransactionRequestModel getById(Integer id) throws Exception {

        Optional<Transactions> res = transactionRepo.findById(id);
        if(res.isEmpty())throw new Exception("Transaction does not exist.");
        Transactions t = res.get();
        return GetTransactionRequestModel.builder()
                .id(t.getId())
                .card(t.getCard())
                .book(t.getBook())
                .bookDueDate(t.getBookDueDate())
                .isIssued(t.getIsIssued())
                .isReturned(t.getIsReturned())
                .fineAmount(t.getFineAmount())
                .status(t.getStatus())
                .createdOn(t.getCreatedOn())
                .updatedOn(t.getUpdatedOn())
                .build();

    }

    public List<Transactions> getByBook(Integer id) throws Exception {
        Book book = bookCRUDService.getBookUsingID(id);
        return transactionRepo.getUsingBook(book);
    }
}
