package com.minorproject.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minorproject.entity.Card;
import com.minorproject.model.ReadCardRequestModel;
import com.minorproject.service.CardCRUDService;

@RestController
@RequestMapping("/card")
public class CardController {
	@Autowired
	CardCRUDService cardCRUDService;

	@PostMapping("/issue")
	public ResponseEntity<?> create(@RequestParam(name = "student_id") Integer student_id) {
		try {
			String res = null;
			res = cardCRUDService.create(student_id);
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

	@GetMapping("/get")
	public ResponseEntity<?> get(@RequestParam(name = "id") Integer id) {
		try {
			Card card = null;
			card = cardCRUDService.get(id);

			ReadCardRequestModel res = ReadCardRequestModel.builder()
					.id(card.getId())
					.status(card.getStatus())
					.createdOn(card.getCreatedOn())
					.updatedOn(card.getUpdatedOn())
					.validThrough(card.getValidThrough())
					.student(card.getStudent())
					.build();

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

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam(name = "id") Integer id) {
		try {
			String res = null;
			res = cardCRUDService.delete(id);
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
