package com.projetDeSemestre.centreDeFormation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projetDeSemestre.centreDeFormation.entities.Admin;
import com.projetDeSemestre.centreDeFormation.exceptions.AlreadyExistsException;
import com.projetDeSemestre.centreDeFormation.exceptions.NotFoundException;
import com.projetDeSemestre.centreDeFormation.repositories.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	AdminRepository rep;
	
	
	

	
	
	public Admin getByUsername(String username)
	{	
		Admin admin = rep.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("Aucun administrateur avec le username "+username+" trouvé"));
		return admin;
	}
	
	
	public List<Admin> getAdmins(Long id)
	{
		
		List<Admin> admins= new ArrayList<Admin>();	
		
		if(id!=null)
			admins.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun administrateur avec l'id "+id+" trouvé")));
		
		else
			admins=rep.findAll();
		
		if(admins.isEmpty())  throw new NotFoundException("Aucun administrateur trouvé");
		return admins;
	}
	
	
	
	public Admin addAdmin(Admin admin) throws AlreadyExistsException
	{
		
		if(rep.findByUsername(admin.getUsername()).isPresent()) {
			System.out.println("Veuillez choisir un autre Username");
			throw new AlreadyExistsException("Veuillez choisir un autre Username");
		}
		
//		if(rep.findByCin(admin.getCin()).isPresent()) {
//			throw new AlreadyExistsException("Un administrateur avec le CIN "+admin.getCin()+" existe déjà");
//		}

		String password = admin.getPassword();
		admin.setPassword(new BCryptPasswordEncoder().encode(password));
		admin.setRole("Admin");
				
		
		rep.save(admin);
		System.out.println("Admin ajouté.");
		return admin;
	
//		Admin user = getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
	}
	
	public void updateAdmin(Long id,Admin admin)
	{
		Admin updated = rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun administrateur avec l'id "+id+" trouvé"));
		
		//verifier l'unicité du nouveau username
		if(rep.findByUsername(admin.getUsername()).isPresent() && !(rep.findByUsername(admin.getUsername()).get()==updated))
			throw new AlreadyExistsException("Veuillez choisir un autre Username");
		//verifier l'unicité du nouveau CIN
		if(rep.findByCin(admin.getCin()).isPresent() && !(rep.findByCin(admin.getCin()).get()==updated))
			throw new AlreadyExistsException("Un administrateur avec le CIN "+admin.getCin()+" existe déjà");
		
		if(admin.getNom()!=null && !admin.getNom().isEmpty()) updated.setNom(admin.getNom());
		if(admin.getPrenom()!=null && !admin.getPrenom().isEmpty()) updated.setPrenom(admin.getPrenom());
		if(admin.getCin()!=null && !admin.getCin().isEmpty()) updated.setCin(admin.getCin());
		if(admin.getTelephone()!=null && !admin.getTelephone().isEmpty()) updated.setTelephone(admin.getTelephone());
		if(admin.getAdresse()!=null && !admin.getAdresse().isEmpty()) updated.setAdresse(admin.getAdresse());
		if(admin.getUsername()!=null && !admin.getUsername().isEmpty()) updated.setUsername(admin.getUsername());
		if(admin.getEmail()!=null && !admin.getEmail().isEmpty()) updated.setEmail(admin.getEmail());
		//if(admin.getPassword()!=null && !admin.getPassword().isEmpty()) updated.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
		
		rep.save(updated);
		
//		if(admin.getPassword()!=null && !admin.getPassword().isEmpty()) updated.setPassword(admin.getPassword());
//		else updated.setPassword(null);
		
		
		//Admin user = getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
	}

	public void removeAdmin(Long id)
	{
		
		//vérifier l'existence de l'administrateur
		Admin admin=rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun administrateur avec l'id "+id+" n'est trouvé"));
		rep.delete(admin);
		
		//Admin user = getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}


	
	
}
