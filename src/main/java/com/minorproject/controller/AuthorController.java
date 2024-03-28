package com.minorproject.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minorproject.entity.Author;
import com.minorproject.model.CreateAuthorRequestModel;
import com.minorproject.model.ReadAuthorRequestModel;
import com.minorproject.model.UpdateAuthorRequestModel;
import com.minorproject.service.AuthorCRUDService;

@RestController
@RequestMapping("/author")
public class AuthorController {
	@Autowired
	AuthorCRUDService authorCRUDService;

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CreateAuthorRequestModel request) {
		try {
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put("status", 1);
			map.put("message", "Success");
			String res = authorCRUDService.create(request);
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
	public ResponseEntity<?> get(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "id", required = false) Integer id) {
		try {
			Author author = null;
			if (id != null)
				author = authorCRUDService.getById(id);
			else if (name != null)
				author = authorCRUDService.getByName(name);
			else if (email != null)
				author = authorCRUDService.getByEmail(email);
			else
				throw new Exception("Unable to get author: wrong parameters provided");
			ReadAuthorRequestModel res = ReadAuthorRequestModel.builder()
					.id(author.getId())
					.email(author.getEmail())
					.name(author.getName())
					.age(author.getAge())
					.country(author.getCountry())
					.build();
					
			Map<String, Object> map = new LinkedHashMap<>();
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

	@PatchMapping("/update")
	public ResponseEntity<?> update(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "id", required = false) Integer id, @RequestBody UpdateAuthorRequestModel request) {
		try {
			String res = null;
			if (id != null)
				res = authorCRUDService.updateById(request, id);
			else if (name != null)
				res = authorCRUDService.updateByName(request, name);
			else if (email != null)
				res = authorCRUDService.updateByEmail(request, email);
			else
				throw new Exception("Updation failed: wrong parameters provided.");

			Map<String, Object> map = new LinkedHashMap<>();
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
	public ResponseEntity<?> update(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "id", required = false) Integer id) {
		try {
			String res = null;
			if (id != null)
				res = authorCRUDService.deleteById(id);
			else if (name != null)
				res = authorCRUDService.deleteByName(name);
			else if (email != null)
				res = authorCRUDService.deleteByEmail(email);
			else
				throw new Exception("Deletion failed: wrong parameters provided.");

			Map<String, Object> map = new LinkedHashMap<>();
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
