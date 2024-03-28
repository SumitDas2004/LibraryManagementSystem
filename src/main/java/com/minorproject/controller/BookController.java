package com.minorproject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minorproject.entity.Book;
import com.minorproject.exception.ForbiddenException;
import com.minorproject.model.CreateBookRequestModel;
import com.minorproject.model.GetBookRequestModel;
import com.minorproject.model.UpdateBookRequestModel;
import com.minorproject.service.BookCRUDService;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookCRUDService bookCRUDService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateBookRequestModel request) {
        try {
            String res = bookCRUDService.create(request);
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 1);
            map.put("message", "Success");
            map.put("data", res);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Seperate the words in the request parameters with '_'. For example insted of
    // writing 'Jhon Doe' write 'Jhon_Doe'
    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "isbn", required = false) String isbn) {
        try {
            Book book = null;
            if (id != null)// Reading a book with id
                book = bookCRUDService.getBookUsingID(id);
            else if (isbn != null)
                book = bookCRUDService.getBookUsingISBN(isbn);
            else if (name != null && author != null)// Reading a book with combination of author name and book name
                book = bookCRUDService.getBookUsingNameAndAuthor(name, author);
            else throw new BadRequestException("Unable to fetch: wrong parameters provided.");

            GetBookRequestModel res = GetBookRequestModel.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .numberOfPages(book.getNumberOfPages())
                .language(book.getLanguage())
                .available(book.getAvailable())
                .genre(book.getGenre())
                .ISBN(book.getISBN())
                .publishedDate(book.getPublishedDate())
                .build();

            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 1);
            map.put("message", "Success");
            map.put("data", res);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(BadRequestException e){
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Seperate the words in the request parameters with '_'. For example insted of
    // writing 'Jhon Doe' write 'Jhon_Doe'
    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateBookRequestModel request,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "isbn", required = false) String isbn) {
        try {
            String res = null;
            if (id != null)
                res =bookCRUDService.updateBookUsingId(id, request);
            else if (isbn != null)
                res = bookCRUDService.updateBookUsingIsbn(isbn, request);
            else if (name != null && author != null)
                res = bookCRUDService.updateBookUsingNameAndAuthor(name, author, request);
            else throw new BadRequestException("Unable to update book: wrong parameters provided.");
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 1);
            map.put("message", "Success");
            map.put("data", res);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(BadRequestException e){
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } catch(ForbiddenException e){
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }catch (Exception e) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "isbn", required = false) String isbn) {
        try {
            String res = null;
            if (id != null)// Reading a book with id
                res = bookCRUDService.deleteBookUsingID(id);
            else if (isbn != null)
                res = bookCRUDService.deleteBookUsingISBN(isbn);
            else if (name != null && author != null)// Reading a book with combination of author name and book name
                res = bookCRUDService.deleteBookUsingNameAndAuthor(name, author);
            else throw new BadRequestException("Unable to delete book: wrong parameters provided.");
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 1);
            map.put("message", "Success");
            map.put("data", res);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(BadRequestException e){
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/genre")
    public ResponseEntity<?> getBooksByGenre(@RequestParam(name = "genre") String genre) {
        try {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            List<GetBookRequestModel> ans = bookCRUDService.getBooksOfSameGenre(genre);
            map.put("status", 1);
            map.put("message", "success");
            map.put("data", ans);
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception e) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
