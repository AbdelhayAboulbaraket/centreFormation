package com.projetDeSemestre.centreDeFormation.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetDeSemestre.centreDeFormation.entities.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long>  {
	Optional<Categorie> findByDesignation(String nom);
}
