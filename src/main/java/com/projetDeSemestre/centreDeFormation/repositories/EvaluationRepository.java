package com.projetDeSemestre.centreDeFormation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetDeSemestre.centreDeFormation.entities.Evaluation;

public interface EvaluationRepository extends JpaRepository <Evaluation,Long> {

}
