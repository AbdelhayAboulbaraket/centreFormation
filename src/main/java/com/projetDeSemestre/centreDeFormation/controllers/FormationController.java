package com.projetDeSemestre.centreDeFormation.controllers;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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

import com.google.gson.Gson;
import com.projetDeSemestre.centreDeFormation.entities.Admin;
import com.projetDeSemestre.centreDeFormation.entities.Formation;
import com.projetDeSemestre.centreDeFormation.services.FormationService;
import com.projetDeSemestre.centreDeFormation.util.FileUploadUtil;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class FormationController {
FormationService service;
	
	@Autowired
	public FormationController(FormationService service) {
		
		this.service=service;
	}
	
	//GET
			@GetMapping("/formations")
			@ResponseStatus(HttpStatus.OK)
			public List<Formation> getFormations(@RequestParam(name="id", required=false) Long id)
			{
				return service.getFormations(id);
			}
			
			
			

			

		
		//POST
			@PostMapping("/formations")
			@ResponseStatus(HttpStatus.CREATED)
			public Formation addFormation(@RequestBody Formation formation) throws IOException
			{	
				System.out.println(formation.getIntervenant().getId());
				Formation maFormation=service.addFormation(formation);
				return maFormation;
			}
			
			@PostMapping("/formationThumbnail/{id}")
			@ResponseStatus(HttpStatus.CREATED)
			public void addFormationThumbnail(@PathVariable Long id,@RequestParam("thumbnail") MultipartFile multipartFile ) throws IOException
			{	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

				Formation p = service.getFormations(id).get(0);
				String nomPhoto="thumbnail"+p.getTitre()+"."+fileName.split("[.]")[1];
				String uploadDir = "src/main/resources/public/thumbnails/";
				p.setThumbnail(uploadDir+nomPhoto);
				service.updateFormation(id, p);	
		        FileUploadUtil.saveFile(uploadDir, nomPhoto, multipartFile);
			}
		
		
		
		//PUT
			
			@PutMapping("/formation/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void updateFormation(@PathVariable Long id , @RequestBody(required=false) Formation formation)
			{	
				service.updateFormation(id,formation);
			}
	
		
			
		//DELETE
			
			@DeleteMapping("/formation/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void deleteFormation(@PathVariable Long id)
			{
				service.removeFormation(id);
			}
}
