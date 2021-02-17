package com.projetDeSemestre.centreDeFormation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projetDeSemestre.centreDeFormation.entities.Formation;
import com.projetDeSemestre.centreDeFormation.entities.Intervenant;
import com.projetDeSemestre.centreDeFormation.entities.Seance;
import com.projetDeSemestre.centreDeFormation.exceptions.AlreadyExistsException;
import com.projetDeSemestre.centreDeFormation.exceptions.NotFoundException;
import com.projetDeSemestre.centreDeFormation.repositories.SeanceRepository;

@Service
public class SeanceService {
	@Autowired
	SeanceRepository rep;
	
//	@Autowired
//	AdminService adminService;
	
	@Autowired
	FormationService formationService;
	
	@Autowired
	IntervenantService intervenantService;
	
	

	
	
	public List<Seance> getSeances(Long id)
	{
		
		List<Seance> seances= new ArrayList<Seance>();	
		
		if(id!=null)
			seances.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune seance avec l'id "+id+" trouvé")));
		
		else seances=rep.findAll();
		
		if(seances.isEmpty())  throw new NotFoundException("Aucune seance n'est trouvé");
		return seances;
	}
	
	
	
	public void addSeance(Seance seance)
	{	
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		//Admin admin = adminService.getByUsername(username);
//		System.out.println(seance);
		Formation formation=formationService.getFormations(seance.getFormation().getId()).get(0);
		seance.setCreneau(seance.getCreneau().plusHours(1));
		seance.setDateFin(seance.getCreneau().plusMinutes(105));
		System.out.println(seance.getCreneau()+" "+seance.getDateFin());
		Intervenant intervenant=intervenantService.getIntervenants(formation.getIntervenant().getId()).get(0);
		if(intervenant.getFormations().size()!=0)
		{
		for(Formation form: intervenant.getFormations())
		{	
			if(form.getSeances().size()!=0)
			{
			for(Seance scea:form.getSeances())
			{
				if((seance.getCreneau().isBefore(scea.getCreneau()) && seance.getDateFin().isBefore(scea.getCreneau()) )
						|| seance.getCreneau().isAfter(scea.getDateFin()) && seance.getDateFin().isAfter(scea.getDateFin())) continue;
				else throw new AlreadyExistsException("Impossible");
			}
			}
		}
		}
		
		rep.save(seance);		
	}
	
	
	public void updateSeance(Long id,Seance seance)
	{
//		Seance updated = rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune seance avec l'id "+id+" trouvé"));
//		
//		
//		if(seance.getTitre()!=null && !seance.getTitre().isEmpty()) updated.setTitre(seance.getTitre());
//		if(seance.getDescription()!=null && !seance.getDescription().isEmpty()) updated.setDescription(seance.getDescription());
//		if(seance.getPrix()!=null) updated.setPrix(seance.getPrix());
//		if(seance.getCategorie()!=null) updated.setCategorie(seance.getCategorie());
//		rep.save(updated);
		rep.save(seance);
	}

	public void removeSeance(Long id)
	{
		
		//vérifier l'existence de la seance
		Seance seance=rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune seance avec l'id "+id+" n'est trouvé"));
		rep.delete(seance);

	}
	
}
