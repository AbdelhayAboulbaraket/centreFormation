package com.projetDeSemestre.centreDeFormation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.projetDeSemestre.centreDeFormation.entities.Admin;
import com.projetDeSemestre.centreDeFormation.entities.Categorie;
import com.projetDeSemestre.centreDeFormation.exceptions.AlreadyExistsException;
import com.projetDeSemestre.centreDeFormation.exceptions.NotFoundException;
import com.projetDeSemestre.centreDeFormation.repositories.AdminRepository;
import com.projetDeSemestre.centreDeFormation.repositories.CategorieRepository;

@Service
public class CategorieService {

	@Autowired
	CategorieRepository rep;
	@Autowired
	AdminService adminService;
	

	
	
	public List<Categorie> getCategories(Long id)
	{
		
		List<Categorie> categories= new ArrayList<Categorie>();	
		
		if(id!=null)
			categories.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune Catégorie avec l'id "+id+" trouvé")));
		
		else
			categories=rep.findAll();
		
		if(categories.isEmpty())  throw new NotFoundException("Aucune Catégorie n'est trouvé");
		return categories;
	}
	
	
	
	public void addCategorie(Categorie categorie)
	{	
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		Admin admin = adminService.getByUsername(username);
		if(rep.findByDesignation(categorie.getDesignation()).isPresent()) {
			throw new AlreadyExistsException("Veuillez choisir un autre nom de catégorie");
		}
		categorie.setCreationAdmin(admin);
		rep.save(categorie);		
	}
	
	
	public void updateCategorie(Long id,Categorie categorie)
	{

		
		rep.save(categorie);
	}

	public void removeCategorie(Long id)
	{
		
		//vérifier l'existence de la catégorie
		Categorie categorie=rep.findById(id).orElseThrow(() -> new NotFoundException("Aucune catégorie avec l'id "+id+" n'est trouvé"));
		rep.delete(categorie);

	}
}
