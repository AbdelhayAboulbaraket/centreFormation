package com.projetDeSemestre.centreDeFormation.controllers;

import java.io.IOException;
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

import com.projetDeSemestre.centreDeFormation.entities.Formation;
import com.projetDeSemestre.centreDeFormation.entities.Intervenant;
import com.projetDeSemestre.centreDeFormation.services.IntervenantService;
import com.projetDeSemestre.centreDeFormation.util.FileUploadUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class IntervenantController {
IntervenantService service;
	
	@Autowired
	public IntervenantController(IntervenantService service) {
		
		this.service=service;
	}
	
	//GET
			@GetMapping("/intervenants")
			@ResponseStatus(HttpStatus.OK)
			public List<Intervenant> getIntervenants(@RequestParam(name="id", required=false) Long id)
			{
				return service.getIntervenants(id);
			}
			@GetMapping("/intervenant/{id}/formations")
			@ResponseStatus(HttpStatus.OK)
			public List<Formation> getFormations(@PathVariable Long id)
			{
				return this.service.getIntervenants(id).get(0).getFormations();
			}
			
			
			@GetMapping("/intervenant/username/{username}")
			@ResponseStatus(HttpStatus.OK)
			public Intervenant getByUsername(@PathVariable(name="username") String username)
			{
				return service.getByUsername(username);
			}
			
			

		
		//POST
			
			@PostMapping("/intervenants")
			@ResponseStatus(HttpStatus.CREATED)
			public Intervenant addIntervenant(@RequestBody Intervenant intervenant ) throws IOException
			{	
				return service.addIntervenant(intervenant);
			}
			
			@PostMapping("/intervenantPicture/{id}")
			@ResponseStatus(HttpStatus.CREATED)
			public void addIntervenant(@PathVariable Long id,@RequestParam("image") MultipartFile multipartFile ) throws IOException
			{	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				Intervenant inter=service.getIntervenants(id).get(0);
				String nomPhoto=inter.getId()+"."+fileName.split("[.]")[1];
				String uploadDir = "src/main/resources/public/photosDeProfil/";
				inter.setImage("http://localhost:8081/photosDeProfil/"+nomPhoto);
				FileUploadUtil.saveFile(uploadDir, nomPhoto, multipartFile);
				service.updateIntervenant(id, inter);
				
		        
		        // a refaire hadshi hada
			}
		
		
		
		//PUT
			
			@PutMapping("/intervenant/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void updateIntervenant(@PathVariable Long id , @RequestBody(required=false) Intervenant intervenant)
			{	
				service.updateIntervenant(id,intervenant);
			}
	
		
			
		//DELETE
			
			@DeleteMapping("/intervenant/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void deleteIntervenant(@PathVariable Long id)
			{
				service.removeIntervenant(id);
			}
}
