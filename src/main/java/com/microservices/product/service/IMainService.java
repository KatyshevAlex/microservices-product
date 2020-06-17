package com.microservices.product.service;


import com.microservices.product.annotations.LogExecutionTime;
import com.microservices.product.entity.Quiz;

import java.util.List;

public interface IMainService {

    @LogExecutionTime
    List<Quiz> getAllQuizzes();

    @LogExecutionTime
    Quiz saveQuiz(Quiz quiz);

    void deleteQuizById(Long id);

    Quiz updateQuiz(Long id, Quiz quiz);

    Quiz getQuizById(Long id);
}
