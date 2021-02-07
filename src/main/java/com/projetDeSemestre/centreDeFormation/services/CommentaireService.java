package com.projetDeSemestre.centreDeFormation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projetDeSemestre.centreDeFormation.entities.Commentaire;
import com.projetDeSemestre.centreDeFormation.entities.User;
import com.projetDeSemestre.centreDeFormation.exceptions.NotFoundException;
import com.projetDeSemestre.centreDeFormation.repositories.CommentaireRepository;
import com.projetDeSemestre.centreDeFormation.repositories.UserRepository;

@Service
public class CommentaireService {
	@Autowired
	CommentaireRepository rep;
	@Autowired
	UserRepository userRepo;
	
	
	public List<Commentaire> getCategories(Long id)
	{
		
		List<Commentaire> commentaires= new ArrayList<Commentaire>();	
		
		if(id!=null)
			commentaires.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun commentaire avec l'id "+id+" trouvé")));
		
		else
			commentaires=rep.findAll();
		
		if(commentaires.isEmpty())  throw new NotFoundException("Aucun commentaire n'est trouvé");
		return commentaires;
	}
	
	
	
	public void addCategorie(Commentaire commentaire)
	{	
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		User user=this.userRepo.findByUsername(username);
//		if(rep.findByNom(commentaire.getNom()).isPresent()) {
//			throw new AlreadyExistsException("Veuillez choisir un autre nom de commentaire");
//		}
		commentaire.setProprietaire(user);
		rep.save(commentaire);		
	}
	
	
	public void updateCategorie(Long id,Commentaire commentaire)
	{
//		Commentaire updated = rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune commentaire avec l'id "+id+" trouvé"));
//		
//		//verifier l'unicité du nouveau nom
//		if(rep.findByNom(commentaire.getNom()).isPresent())
//			throw new AlreadyExistsException("Veuillez choisir un autre Username");
//		
//		if(commentaire.getNom()!=null && !commentaire.getNom().isEmpty()) updated.setNom(commentaire.getNom());
//		if(commentaire.getDescription()!=null && !commentaire.getDescription().isEmpty()) updated.setDescription(commentaire.getDescription());
//		
//		rep.save(updated);
	}

	public void removeCategorie(Long id)
	{
		
		//vérifier l'existence de la commentaire
		Commentaire commentaire=rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun commentaire avec l'id "+id+" n'est trouvé"));
		rep.delete(commentaire);

	}
	
}
