package com.minorproject.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.minorproject.Repo.BookRepo;
import com.minorproject.entity.Author;
import com.minorproject.entity.Book;
import com.minorproject.exception.ForbiddenException;
import com.minorproject.model.CreateBookRequestModel;
import com.minorproject.model.GetBookRequestModel;
import com.minorproject.model.UpdateBookRequestModel;

@Service
public class BookCRUDService {

    @Autowired
    AuthorCRUDService authorCRUDService;

    @Autowired
    BookRepo bookRepo;

    @Autowired
    CacheManager cache;

    public String create(CreateBookRequestModel request) throws Exception {
        Author auth = authorCRUDService.getByName(request.getAuthorName());

        Book book = request.toBook();
        book.setAuthor(auth);

        Book res = bookRepo.save(book);

        return "Successfully created book with id: " + res.getId();
    }

    @Cacheable(value="LibraryManagement_Book_Id", key="#id", unless="#result==null")
    public Book getBookUsingID(Integer id) throws Exception {
        Optional<Book> response = bookRepo.findById(id);
        if(!response.isPresent())throw new Exception("Book does not exist.");

        Book book = response.get();

        return book;
    }

    @Cacheable(value="LibraryManagement_Book_ISBN", key="#isbn", unless="#result==null")
    public Book getBookUsingISBN(String isbn) throws Exception {
        Book book = bookRepo.getByISBN(isbn);

       return book;
    }

    @Cacheable(value="LibraryManagement_Book_NameAndAuthor", key="{#name, #authorName}", unless="#result==null")
    public Book getBookUsingNameAndAuthor(String name, String authorName) throws Exception {
        Author auth = authorCRUDService.getByName(String.join(" ", authorName.split("_")));

        Book book = bookRepo.getByNameAndAuthor(String.join(" ", name.split("_")), auth);

        return book;
    }

    public String updateBookUsingId(Integer id, UpdateBookRequestModel request) throws Exception {


        Book book = getBookUsingID(id);

        if (request.getAvailable() != null)
            book.setAvailable(request.getAvailable());
        if (request.getGenre() != null)
            book.setGenre(request.getGenre());
        if (request.getISBN() != null)
            book.setISBN(request.getISBN());
        if (request.getLanguage() != null)
            book.setLanguage(request.getLanguage());
        if (request.getName() != null)
            book.setName(request.getName());
        if (request.getNumberOfPages() != null)
            book.setNumberOfPages(request.getNumberOfPages());
        if (request.getPublishedDate() != null)
            book.setPublishedDate(request.getPublishedDate());

        if (request.getAuthorName() != null) {
            Author newAuthor = authorCRUDService.getByName(request.getAuthorName());
            if (newAuthor != null)
                book.setAuthor(newAuthor);
            else {
                throw new ForbiddenException("Unable to update book details: Author does not Exist.");
            }
        }

        updateBook(book);

        return "Successfully updated book details.";
    }

    public String updateBookUsingIsbn(String isbn, UpdateBookRequestModel request) throws Exception {

        Book book = getBookUsingISBN(isbn);

        if (request.getAvailable() != null)
            book.setAvailable(request.getAvailable());
        if (request.getGenre() != null)
            book.setGenre(request.getGenre());
        if (request.getISBN() != null)
            book.setISBN(request.getISBN());
        if (request.getLanguage() != null)
            book.setLanguage(request.getLanguage());
        if (request.getName() != null)
            book.setName(request.getName());
        if (request.getNumberOfPages() != null)
            book.setNumberOfPages(request.getNumberOfPages());
        if (request.getPublishedDate() != null)
            book.setPublishedDate(request.getPublishedDate());

        if (request.getAuthorName() != null) {
            Author newAuthor = authorCRUDService.getByName(request.getAuthorName());
            if (newAuthor != null)
                book.setAuthor(newAuthor);
            else {
                throw new ForbiddenException("Unable to update book details: Author does not Exist.");
            }
        }

        updateBook(book);

        return "Successfully updated book details.";
    }

    public String updateBookUsingNameAndAuthor(String name, String authorName, UpdateBookRequestModel request)
            throws Exception {
        Book book = getBookUsingNameAndAuthor(name, authorName);

        if (request.getAvailable() != null)
            book.setAvailable(request.getAvailable());
        if (request.getGenre() != null)
            book.setGenre(request.getGenre());
        if (request.getISBN() != null)
            book.setISBN(request.getISBN());
        if (request.getLanguage() != null)
            book.setLanguage(request.getLanguage());
        if (request.getName() != null)
            book.setName(request.getName());
        if (request.getNumberOfPages() != null)
            book.setNumberOfPages(request.getNumberOfPages());
        if (request.getPublishedDate() != null)
            book.setPublishedDate(request.getPublishedDate());

        if (request.getAuthorName() != null) {
            Author newAuthor = authorCRUDService.getByName(request.getAuthorName());
            if (newAuthor != null)
                book.setAuthor(newAuthor);
            else {
                throw new ForbiddenException("Unable to update book details: Author does not Exist.");
            }
        }

        updateBook(book);

        return "Successfully updated book details.";
    }

    public void updateBook(Book book)throws Exception{
        cache.getCache("LibraryManagement_Book_Id").put(book.getId(), book);
        cache.getCache("LibraryManagement_Book_ISBN").put(book.getISBN(), book);
        cache.getCache("LibraryManagement_Book_NameAndAuthor").put(book.getId()+" "+book.getAuthor().getName(), book);

        bookRepo.save(book);
    }

    public String deleteBookUsingID(Integer id) throws Exception {
        Book book = getBookUsingID(id);
        deleteBook(book);
        return "Successfully deleted book.";
    }

    public String deleteBookUsingISBN(String isbn) throws Exception{
        Book book = getBookUsingISBN(isbn);

        deleteBook(book);

        return "Successfully deleted book";
    }

    public String deleteBookUsingNameAndAuthor(String name, String authorName) throws Exception {

       Book book = getBookUsingNameAndAuthor(name, authorName);
       deleteBook(book);

        return "Successfully deleted book.";
    }

    public ArrayList<GetBookRequestModel> getBooksOfSameGenre(String genre) throws Exception {

        ArrayList<Book> books = bookRepo.getBookByGenre(String.join(" ", genre.split("_")));

        ArrayList<GetBookRequestModel> list = new ArrayList<>();

        for (Book book : books) {
            list.add(GetBookRequestModel.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .author(book.getAuthor())
                    .numberOfPages(book.getNumberOfPages())
                    .language(book.getLanguage())
                    .available(book.getAvailable())
                    .genre(book.getGenre())
                    .ISBN(book.getISBN())
                    .publishedDate(book.getPublishedDate())
                    .build());
        }

        return list;
    }

    public void deleteBook(Book book)throws Exception{
        cache.getCache("LibraryManagement_Book_Id").evict(book.getId());
        cache.getCache("LibraryManagement_Book_ISBN").evict(book.getISBN());
        cache.getCache("LibraryManagement_Book_NameAndAuthor").evict(book.getId()+" "+book.getAuthor().getName());

        bookRepo.delete(book);
    }
}