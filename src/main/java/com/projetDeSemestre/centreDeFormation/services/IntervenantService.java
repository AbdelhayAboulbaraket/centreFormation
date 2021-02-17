package com.projetDeSemestre.centreDeFormation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projetDeSemestre.centreDeFormation.entities.Intervenant;
import com.projetDeSemestre.centreDeFormation.exceptions.AlreadyExistsException;
import com.projetDeSemestre.centreDeFormation.exceptions.NotFoundException;
import com.projetDeSemestre.centreDeFormation.repositories.IntervenantRepository;

@Service
public class IntervenantService {
	@Autowired
	IntervenantRepository rep;
	
	
	

	
	
	public Intervenant getByUsername(String username)
	{	
		Intervenant intervenant = rep.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("Aucun intervenant avec le username "+username+" trouvé"));
		return intervenant;
	}
	
	
	public List<Intervenant> getIntervenants(Long id)
	{
		
		List<Intervenant> etudiants= new ArrayList<Intervenant>();	
		
		if(id!=null)
			etudiants.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun intervenant avec l'id "+id+" trouvé")));
		
		else
			etudiants=rep.findAll();
		
		if(etudiants.isEmpty())  throw new NotFoundException("Aucun intervenant trouvé");
		return etudiants;
	}
	
	
	
	public Intervenant addIntervenant(Intervenant intervenant)
	{
		
		if(rep.findByUsername(intervenant.getUsername()).isPresent()) {
			throw new AlreadyExistsException("Veuillez choisir un autre Username");
		}
		
//		if(rep.findByCin(intervenant.getCin()).isPresent()) {
//			throw new AlreadyExistsException("Un intervenant avec le CIN "+intervenant.getCin()+" existe déjà");
//		}

		String password = intervenant.getPassword();
		intervenant.setPassword(new BCryptPasswordEncoder().encode(password));
		intervenant.setRole("Intervenant");
				
		
		Intervenant inter=rep.save(intervenant);
		return inter;
		//System.out.println("Intervenant ajouté.");
	
//		Intervenant user = getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
	}
	
	public void updateIntervenant(Long id,Intervenant intervenant)
	{
		Intervenant updated = rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun intervenant avec l'id "+id+" trouvé"));
		
		//verifier l'unicité du nouveau username
		if(rep.findByUsername(intervenant.getUsername()).isPresent() && !(rep.findByUsername(intervenant.getUsername()).get()==updated))
			throw new AlreadyExistsException("Veuillez choisir un autre Username");
		//verifier l'unicité du nouveau CIN
		if(rep.findByCin(intervenant.getCin()).isPresent() && !(rep.findByCin(intervenant.getCin()).get()==updated))
			throw new AlreadyExistsException("Un intervenant avec le CIN "+intervenant.getCin()+" existe déjà");
		
		if(intervenant.getNom()!=null && !intervenant.getNom().isEmpty()) updated.setNom(intervenant.getNom());
		if(intervenant.getPrenom()!=null && !intervenant.getPrenom().isEmpty()) updated.setPrenom(intervenant.getPrenom());
		if(intervenant.getCin()!=null && !intervenant.getCin().isEmpty()) updated.setCin(intervenant.getCin());
		if(intervenant.getTelephone()!=null && !intervenant.getTelephone().isEmpty()) updated.setTelephone(intervenant.getTelephone());
		if(intervenant.getAdresse()!=null && !intervenant.getAdresse().isEmpty()) updated.setAdresse(intervenant.getAdresse());
		if(intervenant.getUsername()!=null && !intervenant.getUsername().isEmpty()) updated.setUsername(intervenant.getUsername());
		if(intervenant.getEmail()!=null && !intervenant.getEmail().isEmpty()) updated.setEmail(intervenant.getEmail());
		//if(intervenant.getPassword()!=null && !intervenant.getPassword().isEmpty()) updated.setPassword(new BCryptPasswordEncoder().encode(intervenant.getPassword()));
		
		rep.save(updated);
		
//		if(intervenant.getPassword()!=null && !intervenant.getPassword().isEmpty()) updated.setPassword(intervenant.getPassword());
//		else updated.setPassword(null);
		
		
		//Intervenant user = getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
	}

	public void removeIntervenant(Long id)
	{
		
		//vérifier l'existence de l'administrateur
		Intervenant intervenant=rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun intervenant avec l'id "+id+" n'est trouvé"));
		rep.delete(intervenant);
		
		//Intervenant user = getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
