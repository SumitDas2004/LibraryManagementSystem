package com.minorproject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minorproject.entity.Transactions;
import com.minorproject.exception.ForbiddenException;
import com.minorproject.model.CreateTransactionRequestModel;
import com.minorproject.model.GetTransactionRequestModel;
import com.minorproject.service.TransactionCRUDService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionCRUDService transactionCRUDService;

    @PostMapping("/issue")
    public ResponseEntity<?> issueBook(@RequestBody CreateTransactionRequestModel request) {
        try {
            String res = transactionCRUDService.issueBook(request);
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 1);
            map.put("message", "Success");
            map.put("data", res);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(ForbiddenException e){
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestBody CreateTransactionRequestModel request) {
        try {
            String res = transactionCRUDService.returnBook(request);
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 1);
            map.put("message", "Success");
            map.put("data", res);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(ForbiddenException e){
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("status", 0);
            map.put("message", "Failed");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getById(@RequestParam Integer id) {
        try {
            GetTransactionRequestModel res = transactionCRUDService.getById(id);
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

    @GetMapping("/getbybook")
    public ResponseEntity<?> getByBook(@RequestParam(name = "id") Integer id) {
        try {
            List<Transactions> transactions = transactionCRUDService.getByBook(id);
            List<GetTransactionRequestModel> res = new ArrayList<>();
            for(Transactions t:transactions){
                res.add(GetTransactionRequestModel.builder()
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
                .build());
            }
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
}
