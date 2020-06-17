package com.microservices.product.controller;

import com.microservices.product.entity.Quiz;
import com.microservices.product.service.IMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    IMainService mainService;

    @GetMapping("/test")
    public String test(){
        return "product-service works";
    }

    @GetMapping("/all-quizzes")
    public List<Quiz> getAll() {
        return mainService.getAllQuizzes();
    }

    @PostMapping("/create-quiz")
    @ResponseStatus(HttpStatus.CREATED)
    public Quiz createQuiz(@RequestBody Quiz quiz ) {
        return mainService.saveQuiz(quiz);
    }

    @GetMapping("/get-quiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Quiz getQuizById(@PathVariable("id") Long id){
        return mainService.getQuizById(id);
    }

    @DeleteMapping("/delete-quiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        mainService.deleteQuizById(id);
    }

    @PutMapping("/update-quiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateQuiz(@PathVariable("id")Long id, @RequestBody Quiz quiz) {
        mainService.updateQuiz(id, quiz);
    }
}
