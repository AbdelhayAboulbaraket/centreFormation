package com.projetDeSemestre.centreDeFormation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projetDeSemestre.centreDeFormation.entities.Etudiant;
import com.projetDeSemestre.centreDeFormation.exceptions.AlreadyExistsException;
import com.projetDeSemestre.centreDeFormation.exceptions.NotFoundException;
import com.projetDeSemestre.centreDeFormation.repositories.EtudiantRepository;

@Service
public class EtudiantService {
	
	@Autowired
	EtudiantRepository rep;
	
	
	

	
	
	public Etudiant getByUsername(String username)
	{	
		Etudiant etudiant = rep.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("Aucun étudiant avec le username "+username+" trouvé"));
		return etudiant;
	}
	
	
	public List<Etudiant> getEtudiants(Long id)
	{
		
		List<Etudiant> etudiants= new ArrayList<Etudiant>();	
		
		if(id!=null)
			etudiants.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun étudiant avec l'id "+id+" trouvé")));
		
		else
			etudiants=rep.findAll();
		
		if(etudiants.isEmpty())  throw new NotFoundException("Aucun étudiant trouvé");
		return etudiants;
	}
	
	
	
	public void addEtudiant(Etudiant etudiant)
	{
		
		if(rep.findByUsername(etudiant.getUsername()).isPresent()) {
			throw new AlreadyExistsException("Veuillez choisir un autre Username");
		}
		
//		if(rep.findByCin(etudiant.getCin()).isPresent()) {
//			throw new AlreadyExistsException("Un étudiant avec le CIN "+etudiant.getCin()+" existe déjà");
//		}

		String password = etudiant.getPassword();
		etudiant.setPassword(new BCryptPasswordEncoder().encode(password));
		etudiant.setRole("Etudiant");
				
		
		rep.save(etudiant);
		System.out.println("Etudiant ajouté.");
	
//		Etudiant user = getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
	}
	
	public void updateEtudiant(Long id,Etudiant etudiant)
	{
		Etudiant updated = rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun étudiant avec l'id "+id+" trouvé"));
		
		//verifier l'unicité du nouveau username
		if(rep.findByUsername(etudiant.getUsername()).isPresent() && !(rep.findByUsername(etudiant.getUsername()).get()==updated))
			throw new AlreadyExistsException("Veuillez choisir un autre Username");
		//verifier l'unicité du nouveau CIN
		if(rep.findByCin(etudiant.getCin()).isPresent() && !(rep.findByCin(etudiant.getCin()).get()==updated))
			throw new AlreadyExistsException("Un étudiant avec le CIN "+etudiant.getCin()+" existe déjà");
		
		if(etudiant.getNom()!=null && !etudiant.getNom().isEmpty()) updated.setNom(etudiant.getNom());
		if(etudiant.getPrenom()!=null && !etudiant.getPrenom().isEmpty()) updated.setPrenom(etudiant.getPrenom());
		if(etudiant.getCin()!=null && !etudiant.getCin().isEmpty()) updated.setCin(etudiant.getCin());
		if(etudiant.getTelephone()!=null && !etudiant.getTelephone().isEmpty()) updated.setTelephone(etudiant.getTelephone());
		if(etudiant.getAdresse()!=null && !etudiant.getAdresse().isEmpty()) updated.setAdresse(etudiant.getAdresse());
		if(etudiant.getUsername()!=null && !etudiant.getUsername().isEmpty()) updated.setUsername(etudiant.getUsername());
		if(etudiant.getEmail()!=null && !etudiant.getEmail().isEmpty()) updated.setEmail(etudiant.getEmail());
		//if(etudiant.getPassword()!=null && !etudiant.getPassword().isEmpty()) updated.setPassword(new BCryptPasswordEncoder().encode(etudiant.getPassword()));
		
		rep.save(updated);
		
//		if(etudiant.getPassword()!=null && !etudiant.getPassword().isEmpty()) updated.setPassword(etudiant.getPassword());
//		else updated.setPassword(null);
		
		
		//Etudiant user = getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
	}

	public void removeEtudiant(Long id)
	{
		
		//vérifier l'existence de l'administrateur
		Etudiant etudiant=rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun étudiant avec l'id "+id+" n'est trouvé"));
		rep.delete(etudiant);
		
		//Etudiant user = getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
