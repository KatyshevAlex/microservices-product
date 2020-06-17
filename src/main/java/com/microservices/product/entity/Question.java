package com.microservices.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@Table(name = "questions", schema = "quiz")
@SequenceGenerator(name = "sq_questions", sequenceName = "sq_questions", allocationSize = 1, schema = "quiz")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_questions")
    @Column(name = "id")
    Long id;

    @Column(name = "question")
    String question;

    @Column(name = "right_answer_id")
    Integer rightAnswerId;

    @OneToMany(mappedBy="question", fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    List<Answer> answers;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="quiz_id")
    Quiz quiz;
}
