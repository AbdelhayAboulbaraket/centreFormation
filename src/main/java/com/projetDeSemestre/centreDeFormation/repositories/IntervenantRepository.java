package com.projetDeSemestre.centreDeFormation.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetDeSemestre.centreDeFormation.entities.Etudiant;
import com.projetDeSemestre.centreDeFormation.entities.Intervenant;

public interface IntervenantRepository extends JpaRepository<Intervenant,Long> {
	Optional<Intervenant> findByUsername(String username);

	Optional<Intervenant> findByCin(String cin);
}
