package com.projetDeSemestre.centreDeFormation.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.projetDeSemestre.centreDeFormation.entities.Commentaire;
import com.projetDeSemestre.centreDeFormation.entities.Seance;
import com.projetDeSemestre.centreDeFormation.entities.Support;
import com.projetDeSemestre.centreDeFormation.repositories.SupportRepository;
import com.projetDeSemestre.centreDeFormation.services.CommentaireService;
import com.projetDeSemestre.centreDeFormation.services.SeanceService;
import com.projetDeSemestre.centreDeFormation.util.FileUploadUtil;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class SeanceController {
	
	SeanceService service;
	@Autowired
	SupportRepository repo;
	
	@Autowired
	CommentaireService commentaireService;
	
	@Autowired
	public SeanceController(SeanceService service) {
		
		this.service=service;
	}
	
	//GET
			@GetMapping("/seances")
			@ResponseStatus(HttpStatus.OK)
			public List<Seance> getSeances(@RequestParam(name="id", required=false) Long id)
			{
				return service.getSeances(id);
			}
			
			

			

		
		//POST
			
			@PostMapping("/seances")
			@ResponseStatus(HttpStatus.CREATED)
			public void addSeance(@RequestBody Seance seance ) throws IOException
			{	
				System.out.println(seance.getFormation().getId());
				service.addSeance(seance);
			}
			
			@PostMapping("/seanceSupport/{id}")
			@ResponseStatus(HttpStatus.CREATED)
			public void AddFormationSupport(@PathVariable Long id,@RequestParam("support") MultipartFile multipartFile) throws IOException
			{	
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				Seance seance=service.getSeances(id).get(0);

				String uploadDir = "src/main/resources/public/supportDeCours/"+seance.getId()+"/";
				
				Support supp=new Support();
				supp.setPath(uploadDir+fileName);
				
				repo.save(supp);
				
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);	
			}
			
			@PostMapping("/seance/{id}/commentaire")
			@ResponseStatus(HttpStatus.CREATED)
			public void addCommentaire(@PathVariable Long id,@RequestBody Commentaire commentaire ) throws IOException
			{	
				Seance seance=this.service.getSeances(id).get(0);
				commentaire.setSeance(seance);
				LocalDateTime currentUtilDate = LocalDateTime.now(); 
				commentaire.setDate(currentUtilDate);
				this.commentaireService.addCategorie(commentaire);
			}
		
		
		
		//PUT
			
			@PutMapping("/seance/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void updateSeance(@PathVariable Long id , @RequestBody(required=false) Seance seance)
			{	
				service.updateSeance(id,seance);
			}
			
			
	
		
			
		//DELETE
			
			@DeleteMapping("/seance/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void deleteSeance(@PathVariable Long id)
			{
				service.removeSeance(id);
			}
}
