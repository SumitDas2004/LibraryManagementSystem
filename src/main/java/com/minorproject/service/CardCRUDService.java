package com.minorproject.service;

import java.time.LocalDateTime;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.minorproject.Enums.CardStatus;
import com.minorproject.Repo.CardRepo;
import com.minorproject.entity.Card;
import com.minorproject.entity.Student;
import com.minorproject.exception.ForbiddenException;


@Service
public class CardCRUDService {
	@Autowired
	StudentCRUDService studentCRUDService;

	@Autowired
	CardRepo cardRepo;
	
	public String create(Integer student_id) throws Exception{
		
			Student stud = studentCRUDService.readStudentUsingId(student_id);

			LocalDateTime curDate = LocalDateTime.now();
			LocalDateTime validThrough = LocalDateTime.now().plusYears(1);

			if(stud.getCard()!=null && stud.getCard().getValidThrough().isAfter(curDate)){
				throw new ForbiddenException("Student already has an active card.");
			}
			

			Card card = Card.builder()
						.status(CardStatus.ACTIVE)
						.validThrough(validThrough)
						.student(stud)
						.build();


			stud.setCard(card);

			cardRepo.save(card);

			studentCRUDService.updateStudent(stud);

			return "Successfully issued new card for student with id: "+student_id;
		
	}
	
	@Cacheable(value="LibraryMangement_Card_Id", key="#id", unless="#result==null")
	public Card get(Integer id) throws Exception{
			Optional<Card> response = cardRepo.findById(id);
			
			if(response.isEmpty())throw new Exception("Card does not exist.");

			Card card = response.get();
	
			return card;
	}
	
	@CacheEvict(value="LibraryMangement_Card_Id", key="#id")
	public String delete(Integer id) throws Exception{

			Card card = get(id);

			Student stud = studentCRUDService.readStudentUsingId(card.getStudent().getId());

			stud.setCard(null);

			studentCRUDService.updateStudent(stud);

			cardRepo.delete(card);
			return "Successfully deleted card with id: "+id;
	}
}
