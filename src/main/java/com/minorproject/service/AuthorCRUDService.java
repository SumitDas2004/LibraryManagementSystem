package com.minorproject.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.minorproject.Repo.AuthorRepo;
import com.minorproject.entity.Author;
import com.minorproject.model.CreateAuthorRequestModel;
import com.minorproject.model.UpdateAuthorRequestModel;

@Service
public class AuthorCRUDService {
	@Autowired
	AuthorRepo repo;

	@Autowired
	CacheManager cache;

	public String create(CreateAuthorRequestModel request) throws Exception {

		Author auth = request.toAuthor();
		Author savedAuth = repo.save(auth);
		return "Successfully created author with id " + savedAuth.getId();

	}


	@Cacheable(value="LibraryManagement_Author_Email", key="(#email).toLowerCase()", unless="#result==null")
	public Author getByEmail(String email) throws Exception {
		Author auth = repo.getByEmail(email);
		return auth;
	}

	@Cacheable(value="LibraryManagement_Author_Id", key="#id", unless="#result==null")
	public Author getById(Integer id) throws Exception {
		Optional<Author> response = repo.findById(id);
        if(!response.isPresent())throw new Exception("Author does not exist.");

        Author auth = response.get();

		return auth;
	}

	@Cacheable(value="LibraryManagement_Author_Name", key="(#name).toLowerCase()", unless="#result==null")
	public Author getByName(String name) throws Exception {
		Author auth = repo.getByName(String.join(" ", name.split("_")));

		return auth;

	}

	public String updateById(UpdateAuthorRequestModel request, Integer id) throws Exception {
		Author auth = getById(id);

		if (request.getEmail() != null)
			auth.setEmail(request.getEmail());
		if (request.getName() != null)
			auth.setName(request.getName());
		if (request.getAge() != null)
			auth.setAge(request.getAge());
		if (request.getCountry() != null)
			auth.setCountry(request.getCountry());

		updateAuthor(auth);

		return "Successfully updated author details.";
	}

	public String updateByEmail(UpdateAuthorRequestModel request, String email) throws Exception {
		Author auth = getByEmail(email);

		if (request.getEmail() != null)
			auth.setEmail(request.getEmail());
		if (request.getName() != null)
			auth.setName(request.getName());
		if (request.getAge() != null)
			auth.setAge(request.getAge());
		if (request.getCountry() != null)
			auth.setCountry(request.getCountry());

		updateAuthor(auth);

		return "Successfully updated author details";
	}

	public String updateByName(UpdateAuthorRequestModel request, String name) throws Exception {
		Author auth = getByName(name);

		if (request.getEmail() != null)
			auth.setEmail(request.getEmail());
		if (request.getName() != null)
			auth.setName(request.getName());
		if (request.getAge() != null)
			auth.setAge(request.getAge());
		if (request.getCountry() != null)
			auth.setCountry(request.getCountry());
			
		updateAuthor(auth);

		return "Successfully updated author details";

	}

	public void updateAuthor(Author auth) throws Exception{
		
		cache.getCache("LibraryManagement_Author_Id").put(auth.getId(), auth);
		cache.getCache("LibraryManagement_Author_Email").put(auth.getEmail().toLowerCase(), auth);
		cache.getCache("LibraryManagement_Author_Name").put(auth.getName().toLowerCase(), auth);

		repo.save(auth);
	}

	public String deleteByEmail(String email) throws Exception {
		Author auth = getByEmail(email);
		deleteAuthor(auth);
		return "Successfully deleted author details.";
	}

	public String deleteById(Integer id) throws Exception {
		Author auth = getById(id);
		deleteAuthor(auth);
		return "Successfully deleted author details.";
	}

	public String deleteByName(String name) throws Exception {

		Author auth = getByName(name);
		deleteAuthor(auth);
		return "Successfully deleted author details";
	}


	public void deleteAuthor(Author auth)throws Exception{
		cache.getCache("LibraryManagement_Author_Id").evict(auth.getId());
		cache.getCache("LibraryManagement_Author_Email").evict(auth.getEmail().toLowerCase());
		cache.getCache("LibraryManagement_Author_Name").evict(auth.getName().toLowerCase());

		repo.delete(auth);

	}

}
