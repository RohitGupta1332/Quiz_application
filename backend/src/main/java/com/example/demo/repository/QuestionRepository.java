package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

    @Query(value = "SELECT * FROM question WHERE category IN ?1 ORDER BY RAND() LIMIT 10", nativeQuery = true)
	List<Question> findByCategoryIn(List<String> list);

	List<Question> findByIdIn(List<Integer> questionIdIntegers);

}
