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

import com.minorproject.entity.Student;
import com.minorproject.exception.BadRequestException;
import com.minorproject.model.CreateStudentRequestModel;
import com.minorproject.model.ReadStudentRequestModel;
import com.minorproject.model.UpdateStudentRequestModel;
import com.minorproject.service.StudentCRUDService;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentCRUDService studentCRUDService;


	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CreateStudentRequestModel request) {
		try {
			String res = studentCRUDService.createStudent(request);
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
	public ResponseEntity<?> delete(@RequestParam(required = false, name = "phone") String phoneNumber,
			@RequestParam(required = false) String email, @RequestParam(required = false) Integer id) {
		try {
			String res = null;
			if (phoneNumber != null)
				res = studentCRUDService.deleteUsingPhoneNumber(phoneNumber);
			else if (email != null)
				res = studentCRUDService.deleteUsingEmail(email);
			else if (id != null)
				res = studentCRUDService.deleteUsingId(id);
			else throw new BadRequestException("Unable to delete Student: Wrong parameters provided.");
			Map<String, Object> map = new LinkedHashMap<>();
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

	@PatchMapping("/update")
	public ResponseEntity<?> update(@RequestParam(required = false, name = "phone") String phoneNumber,
			@RequestParam(required = false) String email, @RequestParam(required = false) Integer id,
			@RequestBody UpdateStudentRequestModel request) {
		try {
			String res = null;
			if (phoneNumber != null)
				res = studentCRUDService.updateUsingPhoneNumber(phoneNumber, request);
			else if (email != null)
				res = studentCRUDService.updateUsingEmail(email, request);
			else if (id != null)
				res = studentCRUDService.updateUsingId(id, request);
			else throw new BadRequestException("Unable to update Student: wrong parameters provided.");
			Map<String, Object> map = new LinkedHashMap<>();
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

	@GetMapping("/get")
	public ResponseEntity<?> get(@RequestParam(required = false, name = "phone") String phoneNumber,
			@RequestParam(required = false) String email, @RequestParam(required = false) Integer id) {
		try {
			Student s = null;
			if (phoneNumber != null)
				s = studentCRUDService.readStudentUsingPhoneNumber(phoneNumber);
			else if (email != null)
				s = studentCRUDService.readStudentUsingEmail(email);
			else if (id != null)
				s = studentCRUDService.readStudentUsingId(id);
			else throw new BadRequestException("Unable to find Student: wrong parameters provided.");


			ReadStudentRequestModel res = ReadStudentRequestModel.builder()
											.age(s.getAge())
											.name(s.getName())
											.email(s.getEmail())
											.country(s.getCountry())
											.phoneNumber(s.getPhoneNumber())
											.id(s.getId())
											.card(s.getCard())
											.createdOn(s.getCreatedOn())
											.updatedOn(s.getUpdatedOn())
											.build();

			Map<String, Object> map = new LinkedHashMap<>();
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
}
