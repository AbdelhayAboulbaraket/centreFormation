package com.projetDeSemestre.centreDeFormation.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.projetDeSemestre.centreDeFormation.entities.Admin;
import com.projetDeSemestre.centreDeFormation.entities.Etudiant;
import com.projetDeSemestre.centreDeFormation.entities.Formation;
import com.projetDeSemestre.centreDeFormation.services.EtudiantService;
import com.projetDeSemestre.centreDeFormation.util.FileUploadUtil;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class EtudiantController {
EtudiantService service;
	
	@Autowired
	public EtudiantController(EtudiantService service) {
		
		this.service=service;
	}
	
	//GET
			@GetMapping("/etudiants")
			@ResponseStatus(HttpStatus.OK)
			public List<Etudiant> getEtudiants(@RequestParam(name="id", required=false) Long id)
			{
				return service.getEtudiants(id);
			}
			
			@GetMapping("/etudiant/{id}/formations")
			@ResponseStatus(HttpStatus.OK)
			public List<Formation> getFormations(@PathVariable Long id)
			{
				List<Formation> forms=service.getEtudiants(id).get(0).getFormations();
				return forms;
			}
			
			
			
			
			@GetMapping("/etudiant/username/{username}")
			@ResponseStatus(HttpStatus.OK)
			public Etudiant getByUsername(@PathVariable(name="username") String username)
			{
				return service.getByUsername(username);
			}
			
			

		
		//POST
			
			@PostMapping("/etudiants")
			@ResponseStatus(HttpStatus.CREATED)
			public void addEtudiant(@RequestBody Etudiant etudiant) throws IOException
			{	
					

				service.addEtudiant(etudiant);

			}
			@PostMapping("/etudiant/inscriptionFormation")
			@ResponseStatus(HttpStatus.CREATED)
			public void addFormation(@RequestBody Formation formation) throws IOException
			{	
				
				service.addFormation(formation);

			}
			
			
			@PostMapping("/etudiantPicture/{id}")
			@ResponseStatus(HttpStatus.CREATED)
			//requestParams@RequestBody Etudiant etudiant,@RequestBody MultipartFile multipartFile
			public void addEtudiant(@PathVariable Long id,MultipartFile multipartFile ) throws IOException
			{	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				Etudiant p = service.getEtudiants(id).get(0);

				String nomPhoto=p.getUsername()+"."+fileName.split("[.]")[1];
				String uploadDir = "src/main/resources/public/photosDeProfil/";
				p.setPhoto(uploadDir+nomPhoto);
				service.updateEtudiant(id, p);
				
		        FileUploadUtil.saveFile(uploadDir, nomPhoto, multipartFile);
		        // a refaire hadshi hada
			}
		
		
		
		//PUT
			
			@PutMapping("/etudiant/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void updateEtudiant(@PathVariable Long id , @RequestBody(required=false) Etudiant etudiant)
			{	
				service.updateEtudiant(id,etudiant);
			}
	
		
			
		//DELETE
			
			@DeleteMapping("/etudiant/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void deleteEtudiant(@PathVariable Long id)
			{
				service.removeEtudiant(id);
			}
}
