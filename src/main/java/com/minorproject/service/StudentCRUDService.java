package com.minorproject.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.minorproject.Repo.CardRepo;
import com.minorproject.Repo.StudentRepo;
import com.minorproject.entity.Student;
import com.minorproject.model.CreateStudentRequestModel;
import com.minorproject.model.UpdateStudentRequestModel;

@Service
public class StudentCRUDService {
	@Autowired
	StudentRepo repo;

	@Autowired
	CardRepo cardRepo;

	@Autowired
	CacheManager cache;

	public String createStudent(CreateStudentRequestModel request) throws Exception {
		Student stud = request.toStudent();

		repo.save(stud);

		String res = "Student " + stud.getName() + " with email " + stud.getEmail() + " created successfully.";

		return res;
	}

	public String deleteUsingPhoneNumber(String number) throws Exception {
		Student stud = readStudentUsingPhoneNumber(number);
		
		deleteStudent(stud);

		String res = "Student with phone number " + number + " deleted successfully.";

		return res;

	}

	public String deleteUsingEmail(String email) throws Exception {
		Student stud = readStudentUsingEmail(email);

		
		deleteStudent(stud);

		String res = "Student with E-mail " + email + " deleted successfully.";

		return res;
	}

	public String deleteUsingId(Integer id) throws Exception{
		Student stud = readStudentUsingId(id);

		deleteStudent(stud);
		
		String res = "Student with id " + id + " deleted successfully.";

		return res;

	}

	public void deleteStudent(Student stud) throws Exception{
		cache.getCache("LibraryManagement_Student_PhoneNumber").evict(stud.getPhoneNumber());
		cache.getCache("LibraryManagement_Student_Id").evict(stud.getId());
		cache.getCache("LibraryManagement_Student_Email").evict(stud.getEmail());

		repo.delete(stud);
	}

	public String updateUsingPhoneNumber(String phone, UpdateStudentRequestModel request) throws Exception {
		Student stud = readStudentUsingPhoneNumber(phone);

		if (request.getName() != null)
			stud.setName(request.getName());
		if (request.getEmail() != null)
			stud.setEmail(request.getEmail());
		if (request.getPhoneNumber() != null)
			stud.setPhoneNumber(request.getPhoneNumber());
		if (request.getCountry() != null)
			stud.setCountry(request.getCountry());
		if (request.getAge() != null)
			stud.setAge(request.getAge());
		
		updateStudent(stud);

		return "Student " + stud.getName() + " with email " + stud.getEmail() + " updated successfull.";
	}

	public String updateUsingEmail(String email, UpdateStudentRequestModel request) throws Exception {

		Student stud = readStudentUsingEmail(email);

		if (request.getName() != null)
			stud.setName(request.getName());
		if (request.getEmail() != null)
			stud.setEmail(request.getEmail());
		if (request.getPhoneNumber() != null)
			stud.setPhoneNumber(request.getPhoneNumber());
		if (request.getCountry() != null)
			stud.setCountry(request.getCountry());
		if (request.getAge() != null)
			stud.setAge(request.getAge());
		
		updateStudent(stud);

		return "Student " + stud.getName() + " with email " + stud.getEmail() + " updated successfull.";

	}


	public String updateUsingId(Integer id, UpdateStudentRequestModel request) throws Exception {

		Student stud = readStudentUsingId(id);

		if (request.getName() != null)
			stud.setName(request.getName());
		if (request.getEmail() != null)
			stud.setEmail(request.getEmail());
		if (request.getPhoneNumber() != null)
			stud.setPhoneNumber(request.getPhoneNumber());
		if (request.getCountry() != null)
			stud.setCountry(request.getCountry());
		if (request.getAge() != null)
			stud.setAge(request.getAge());
			
		updateStudent(stud);

		return "Student " + stud.getName() + " with email " + stud.getEmail() + " updated successfull.";

	}

	public void updateStudent(Student stud) throws Exception{

		cache.getCache("LibraryManagement_Student_PhoneNumber").put(stud.getPhoneNumber(), stud);
		cache.getCache("LibraryManagement_Student_Id").put(stud.getId(), stud);
		cache.getCache("LibraryManagement_Student_Email").put(stud.getEmail(), stud);

		repo.save(stud);
	}

	@Cacheable(value="LibraryManagement_Student_PhoneNumber", key="#phoneNumber", unless="#result==null")
	public Student readStudentUsingPhoneNumber(String phoneNumber) throws Exception {
		Student s = repo.getByPhone(phoneNumber);

		return s;
	}

	@Cacheable(value="LibraryManagement_Student_Email", key="#email", unless="#result==null")
	public Student readStudentUsingEmail(String email) throws Exception {
		Student s = repo.getByEmail(email);

		return s;
	}

	@Cacheable(value="LibraryManagement_Student_Id", key="#id", unless="#result==null")
	public Student readStudentUsingId(Integer id) throws Exception {
		Optional<Student> response = repo.findById(id);

		if(response.isEmpty())throw new Exception("Student does not exist.");

		Student s = response.get();

		return s;
	}

}
