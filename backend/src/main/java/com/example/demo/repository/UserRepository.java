package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

	boolean existsByEmail(String email);

	boolean existsByEmailAndPassword(String email, String password);

}
