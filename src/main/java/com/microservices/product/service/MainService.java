package com.microservices.product.service;

import com.microservices.product.entity.Quiz;
import com.microservices.product.repository.AnswerRepo;
import com.microservices.product.repository.QuestionRepo;
import com.microservices.product.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("MainService")
@Profile({"production", "test"})
@Transactional
public class MainService implements IMainService {

    private final QuizRepo quizRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;

    @Autowired
    public MainService(QuizRepo quizRepo, QuestionRepo questionRepo, AnswerRepo answerRepo) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }

    @Override
    @Transactional
    public Quiz saveQuiz(Quiz quiz) {
        Quiz quizWithId = quizRepo.save(quiz);
        quiz.getQuestions().stream().peek((question) ->{ // for each question in the quiz we must do two operations:
            question.setQuiz(quizWithId); // assign Quiz by id
            questionRepo.saveAndFlush(question); //save the question for getting ID
            question.getAnswers().stream().peek((answer) -> {
                answer.setQuestion(question); //here we assign the question for  the answer
                answerRepo.saveAndFlush(answer);
            });

        });
        System.out.println(quiz);
        return quiz;
    }

    @Override
    @Transactional
    public void deleteQuizById(Long id) {
        quizRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Quiz updateQuiz(Long id, Quiz quiz) {
        Quiz q = quizRepo.getOne(id);
        q.setQuestions(quiz.getQuestions());
        return q;
    }

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepo.getOne(id);
    }

}
