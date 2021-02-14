package com.projetDeSemestre.centreDeFormation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projetDeSemestre.centreDeFormation.entities.Admin;
import com.projetDeSemestre.centreDeFormation.entities.Formation;
import com.projetDeSemestre.centreDeFormation.exceptions.NotFoundException;
import com.projetDeSemestre.centreDeFormation.repositories.FormationRepository;
@Service
public class FormationService {
	@Autowired
	FormationRepository rep;
	@Autowired
	AdminService adminService;
	

	
	
	public List<Formation> getFormations(Long id)
	{
		
		List<Formation> formations= new ArrayList<Formation>();	
		
		if(id!=null)
			formations.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune formation avec l'id "+id+" trouvé")));
		
		else formations=rep.findAll();
		
		if(formations.isEmpty())  throw new NotFoundException("Aucune formation n'est trouvé");
		return formations;
	}
	
	
	
	public Formation addFormation(Formation formation)
	{	
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		Admin admin = adminService.getByUsername(username);
		formation.setCreationAdmin(admin);
		
		return rep.save(formation);
	}
	
	
	public void updateFormation(Long id,Formation formation)
	{
		Formation updated = rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune formation avec l'id "+id+" trouvé"));
		
		
		if(formation.getTitre()!=null && !formation.getTitre().isEmpty()) updated.setTitre(formation.getTitre());
		if(formation.getDescription()!=null && !formation.getDescription().isEmpty()) updated.setDescription(formation.getDescription());
		if(formation.getPrix()!=null) updated.setPrix(formation.getPrix());
		if(formation.getCategorie()!=null) updated.setCategorie(formation.getCategorie());
		rep.save(updated);
	}

	public void removeFormation(Long id)
	{
		
		//vérifier l'existence de la formation
		Formation formation=rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune formation avec l'id "+id+" n'est trouvé"));
		rep.delete(formation);

	}
}
