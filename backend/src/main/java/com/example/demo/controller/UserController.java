package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Question;
import com.example.demo.entity.User;
import com.example.demo.model.Answer;
import com.example.demo.model.CategoriesSelected;
import com.example.demo.model.ListOfAnswer;
import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;


@RestController
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	QuestionService questionService;
	
	@GetMapping("/get")
	public ResponseEntity<List<User>> getData(){
		return userService.findAll();
	}
	
	@PostMapping("/save")
	public ResponseEntity<Object> saveData(@RequestBody User user){
		return userService.save(user);
	}
	
	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestParam("email") String email, @RequestParam("password") String password) {
		
		return userService.doesExists(email, password);
	}
	
	@GetMapping("/questions")
	public ResponseEntity<List<Question>> getQuestion(){
		return questionService.getQuestions();
	}
	
	@PostMapping("/categories")
	public ResponseEntity<List<Question>> receiveCategories(@RequestBody CategoriesSelected categoriesSelected) {
		return getQuestions(categoriesSelected.getCategories());
	}
	public ResponseEntity<List<Question>> getQuestions(List<String> list){
		return questionService.getQuestionsByCategories(list);
	}
	
	@PostMapping("/answers")
	public ResponseEntity<Integer> checkAnswers(@RequestBody ListOfAnswer answers){

		try {
			List<Answer> list = answers.getAnswers();
			int count = 0;
			
			List<Integer> questionIdIntegers = new ArrayList<Integer>();
			
			for(Answer answer : list) {
				questionIdIntegers.add(answer.getQuestionId());
			}
			List<Question> questions = questionService.getQuestionById(questionIdIntegers);
			
			Map<Integer, Question> map = new HashMap<Integer, Question>();
			for(Question question: questions) {
				map.put(question.getId(), question);
			}

			for(Answer answer: list) {
				if(map.containsKey(answer.getQuestionId())) {
					Question question = map.get(answer.getQuestionId());
					if(question.getRight_option().equals(answer.getAnswer())) {
						count++;
					}
				}
			}
			return new ResponseEntity<Integer>(count, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
