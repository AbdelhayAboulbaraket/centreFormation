package com.projetDeSemestre.centreDeFormation.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetDeSemestre.centreDeFormation.entities.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {

	Optional<Etudiant> findByUsername(String username);

	Optional<Etudiant> findByCin(String cin);
}
