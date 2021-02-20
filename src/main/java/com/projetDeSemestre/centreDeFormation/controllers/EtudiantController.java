package com.projetDeSemestre.centreDeFormation.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
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

import com.projetDeSemestre.centreDeFormation.entities.BooleanResponse;
import com.projetDeSemestre.centreDeFormation.entities.Etudiant;
import com.projetDeSemestre.centreDeFormation.entities.Formation;
import com.projetDeSemestre.centreDeFormation.entities.PassageEvaluation;
import com.projetDeSemestre.centreDeFormation.services.EtudiantService;
import com.projetDeSemestre.centreDeFormation.services.FormationService;
import com.projetDeSemestre.centreDeFormation.util.FileUploadUtil;
@RestController
@RequestMapping(value = "/api")
public class EtudiantController {
EtudiantService service;
	
	@Autowired
	public EtudiantController(EtudiantService service) {
		
		this.service=service;
	}
	@Autowired
	FormationService formationService;
	
	//GET
			@GetMapping("/etudiants")
			@ResponseStatus(HttpStatus.OK)
			public List<Etudiant> getEtudiants(@RequestParam(name="id", required=false) Long id)
			{
				return service.getEtudiants(id);
			}
			
			@GetMapping("/etudiant/formations")
			@ResponseStatus(HttpStatus.OK)
			public List<Formation> getFormations()
			{	String username=SecurityContextHolder.getContext().getAuthentication().getName();
			Etudiant etud=this.service.getByUsername(username);
			
				return etud.getFormations();
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
			
			@PostMapping("/inscriptionEtudiant")
			@ResponseStatus(HttpStatus.CREATED)
			public Etudiant insriptionEtudiant(@RequestBody Etudiant etudiant) throws IOException
			{	
					
				return service.addEtudiant(etudiant);
			}
			
			@PostMapping("/etudiant/inscriptionFormation")
			@ResponseStatus(HttpStatus.CREATED)
			public void addFormation(@RequestBody Formation formation) throws IOException
			{	
				
				service.addFormation(formation);

			}
			
			@GetMapping("/dejaPasse/{id}")
			@ResponseStatus(HttpStatus.OK)
			public BooleanResponse dejaPasse(@PathVariable Long id)
			{
				BooleanResponse b=new BooleanResponse();
				String username=SecurityContextHolder.getContext().getAuthentication().getName();
				Etudiant etud=this.service.getByUsername(username);
				
				if(this.formationService.getFormations(id).get(0).getEvaluation()==null) {
					b.setResponse(false);
					return b;
				}
				List<PassageEvaluation> passages=this.formationService.getFormations(id).get(0).getEvaluation().getPassages();
				
				for(PassageEvaluation passage:passages)
				{
					if(passage.getEtudiant().getId()==etud.getId()) {
						b.setResponse(true);
						return b;
					}
				}
				b.setResponse(false);
				return b;
			}
			
			@PostMapping("/etudiant/estInscrit")
			@ResponseStatus(HttpStatus.CREATED)
			public BooleanResponse estInscrit(@RequestBody Formation formation) throws IOException
			{	
				String username=SecurityContextHolder.getContext().getAuthentication().getName();
				Etudiant etud=this.service.getByUsername(username);
				Formation laFormation=this.formationService.getFormations(formation.getId()).get(0);
				BooleanResponse b=new BooleanResponse();
				for(Formation forma:etud.getFormations())
				{	
					System.out.println(forma.getId()+" vs "+laFormation.getId());
					if (forma.getId()==laFormation.getId()) {
						b.setResponse(true);
						return b;
					}
				}
				b.setResponse(false);
				return b;
			}
			
			
			@PostMapping("/etudiantPicture/{id}")
			@ResponseStatus(HttpStatus.CREATED)
			//requestParams@RequestBody Etudiant etudiant,@RequestBody MultipartFile multipartFile
			public void addEtudiantPicture(@PathVariable Long id,@RequestParam("image") MultipartFile multipartFile ) throws IOException
			{	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				Etudiant p = service.getEtudiants(id).get(0);

				String nomPhoto=p.getId()+"."+fileName.split("[.]")[1];
				String uploadDir = "src/main/resources/public/photosDeProfil/";
				p.setImage("http://localhost:8081/photosDeProfil/"+nomPhoto);
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
