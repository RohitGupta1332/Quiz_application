package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Question;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	public ResponseEntity<List<User>> findAll() {
		try {
			return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	public ResponseEntity<Object> save(User user) {
		try {
			if(doesExists(user.getEmail())) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			}else {
				userRepository.save(user);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	public boolean doesExists(String email) {
		return userRepository.existsByEmail(email);
	}

	public ResponseEntity<String> doesExists(String email, String password) {
		try {
			if(userRepository.existsByEmailAndPassword(email, password)) {
				return new ResponseEntity<String>("Login successful", HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Invalid email or password", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
