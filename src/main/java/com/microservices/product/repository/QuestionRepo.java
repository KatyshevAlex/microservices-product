package com.microservices.product.repository;


import com.microservices.product.entity.Question;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("production")
public interface QuestionRepo extends JpaRepository<Question, Long> {
}
