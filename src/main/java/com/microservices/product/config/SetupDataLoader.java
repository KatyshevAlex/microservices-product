package com.microservices.product.config;

import com.microservices.product.entity.Answer;
import com.microservices.product.entity.Question;
import com.microservices.product.entity.Quiz;
import com.microservices.product.repository.AnswerRepo;
import com.microservices.product.repository.QuestionRepo;
import com.microservices.product.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private AnswerRepo answerRepo;

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private QuizRepo quizRepo;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        Quiz quiz = quizRepo.save(new Quiz("FirstQuiz"));

        Question q1 = new Question();
        Question q2 = new Question();
        q1.setQuiz(quiz);
        q1.setRightAnswerId(3);
        q1.setQuestion("Do you know Java?");
        q2.setQuiz(quiz);
        q2.setRightAnswerId(2);
        q2.setQuestion("Do you know Spring Boot?");
        questionRepo.save(q1);
        questionRepo.save(q2);
        Answer a1 = new Answer();
        Answer a2 = new Answer();
        Answer a3 = new Answer();
        Answer a4 = new Answer();
        a1.setText("I know Java");
        a2.setText("I don't know Java");
        a3.setText("I know Spring Boot");
        a4.setText("I don't know Spring Boot");
        a1.setQuestion(q1);
        a2.setQuestion(q1);
        a3.setQuestion(q2);
        a4.setQuestion(q2);
        answerRepo.save(a1);
        answerRepo.save(a2);
        answerRepo.save(a3);
        answerRepo.save(a4);

        q1.setAnswers(Arrays.asList(a1, a2));
        q2.setAnswers(Arrays.asList(a3, a4));

        quiz.setQuestions(Arrays.asList(q1, q2));

        alreadySetup = true;
    }

}
