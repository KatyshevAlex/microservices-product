package com.microservices.product.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@Table(name = "quizzes", schema = "quiz")
@SequenceGenerator(name = "sq_quizzes", sequenceName = "sq_quizzes", allocationSize = 1, schema = "quiz")
public class Quiz {

    public Quiz(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_quizzes")
    Long id;

    @Column(name = "name")
    String name;


    @OneToMany(mappedBy="quiz", fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    List<Question> questions;
}
