package com.projetDeSemestre.centreDeFormation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetDeSemestre.centreDeFormation.entities.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire,Long> {

}
