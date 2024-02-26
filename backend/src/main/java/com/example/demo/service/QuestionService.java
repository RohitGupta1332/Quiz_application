package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepo;
	
	public ResponseEntity<List<Question>> getQuestions() {
		try {
			return new ResponseEntity<List<Question>>(questionRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Question>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<Question>> getQuestionsByCategories(List<String> list) {
		return new ResponseEntity<List<Question>>(questionRepo.findByCategoryIn(list), HttpStatus.OK);
	}

	public List<Question> getQuestionById(List<Integer> questionIdIntegers) {
		return questionRepo.findByIdIn(questionIdIntegers);
	}

	
}
