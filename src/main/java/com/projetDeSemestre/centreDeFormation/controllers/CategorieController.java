package com.projetDeSemestre.centreDeFormation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projetDeSemestre.centreDeFormation.entities.Categorie;
import com.projetDeSemestre.centreDeFormation.entities.Formation;
import com.projetDeSemestre.centreDeFormation.services.CategorieService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api")
public class CategorieController {
CategorieService service;
	
	@Autowired
	public CategorieController(CategorieService service) {
		
		this.service=service;
	}
	
	//GET
			@GetMapping("/categories")
			@ResponseStatus(HttpStatus.OK)
			public List<Categorie> getCategories(@RequestParam(name="id", required=false) Long id)
			{
				return service.getCategories(id);
			}
			
			@GetMapping("/categories/{id}/formations")
			@ResponseStatus(HttpStatus.OK)
			public List<Formation> getFormations(@PathVariable Long id)
			{
				List<Formation> forms=service.getCategories(id).get(0).getFormations();
				return forms;
			}
			
			

			

		
		//POST
			
			@PostMapping("/categories")
			@ResponseStatus(HttpStatus.CREATED)
			public void addCategorie(@RequestBody Categorie categorie)
			{
				service.addCategorie(categorie);
			}
		
		
		
		//PUT
			
			@PutMapping("/categorie/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void updateCategorie(@PathVariable Long id , @RequestBody(required=false) Categorie categorie)
			{	
				service.updateCategorie(id,categorie);
			}
	
		
			
		//DELETE
			
			@DeleteMapping("/categorie/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void deleteCategorie(@PathVariable Long id)
			{
				service.removeCategorie(id);
			}
}
