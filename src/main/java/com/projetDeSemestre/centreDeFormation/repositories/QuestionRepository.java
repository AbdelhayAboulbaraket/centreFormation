package com.projetDeSemestre.centreDeFormation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetDeSemestre.centreDeFormation.entities.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {

}
