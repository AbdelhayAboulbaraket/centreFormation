package com.projetDeSemestre.centreDeFormation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projetDeSemestre.centreDeFormation.entities.Evaluation;
import com.projetDeSemestre.centreDeFormation.exceptions.NotFoundException;
import com.projetDeSemestre.centreDeFormation.repositories.EvaluationRepository;
import com.projetDeSemestre.centreDeFormation.repositories.UserRepository;


@Service
public class EvaluationService {

	
	@Autowired
	EvaluationRepository rep;
	@Autowired
	UserRepository userRepo;
	
	
	public List<Evaluation> getEvaluations(Long id)
	{
		
		List<Evaluation> evaluations= new ArrayList<Evaluation>();	
		
		if(id!=null)
			evaluations.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun evaluation avec l'id "+id+" trouvé")));
		
		else
			evaluations=rep.findAll();
		
		if(evaluations.isEmpty())  throw new NotFoundException("Aucun evaluation n'est trouvé");
		return evaluations;
	}
	
	
	
	public void addEvaluation(Evaluation evaluation)
	{	
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		//User user=this.userRepo.findByUsername(username);
//		if(rep.findByNom(evaluation.getNom()).isPresent()) {
//			throw new AlreadyExistsException("Veuillez choisir un autre nom de evaluation");
//		}
		//evaluation.setProprietaire(user);
		rep.save(evaluation);		
	}
	
	
	public void updateEvaluation(Long id,Evaluation evaluation)
	{
//		Evaluation updated = rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune evaluation avec l'id "+id+" trouvé"));
//		
//		//verifier l'unicité du nouveau nom
//		if(rep.findByNom(evaluation.getNom()).isPresent())
//			throw new AlreadyExistsException("Veuillez choisir un autre Username");
//		
//		if(evaluation.getNom()!=null && !evaluation.getNom().isEmpty()) updated.setNom(evaluation.getNom());
//		if(evaluation.getDescription()!=null && !evaluation.getDescription().isEmpty()) updated.setDescription(evaluation.getDescription());
//		
//		rep.save(updated);
	}

	public void removeEvaluation(Long id)
	{
		
		//vérifier l'existence de la evaluation
		Evaluation evaluation=rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun evaluation avec l'id "+id+" n'est trouvé"));
		rep.delete(evaluation);

	}
	
}
