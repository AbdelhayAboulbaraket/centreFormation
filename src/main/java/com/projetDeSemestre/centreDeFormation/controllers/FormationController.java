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

import com.projetDeSemestre.centreDeFormation.entities.Choix;
import com.projetDeSemestre.centreDeFormation.entities.Etudiant;
import com.projetDeSemestre.centreDeFormation.entities.Evaluation;
import com.projetDeSemestre.centreDeFormation.entities.Formation;
import com.projetDeSemestre.centreDeFormation.entities.Question;
import com.projetDeSemestre.centreDeFormation.repositories.ChoixRepository;
import com.projetDeSemestre.centreDeFormation.repositories.EvaluationRepository;
import com.projetDeSemestre.centreDeFormation.repositories.FormationRepository;
import com.projetDeSemestre.centreDeFormation.repositories.QuestionRepository;
import com.projetDeSemestre.centreDeFormation.services.EtudiantService;
import com.projetDeSemestre.centreDeFormation.services.EvaluationService;
import com.projetDeSemestre.centreDeFormation.services.FormationService;
import com.projetDeSemestre.centreDeFormation.util.FileUploadUtil;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class FormationController {
	
@Autowired
QuestionRepository questionRepository;

@Autowired
EvaluationRepository evaluationRepository;

@Autowired
ChoixRepository choixRepository;

@Autowired
FormationRepository rep;
	
@Autowired
FormationService service;

@Autowired
EtudiantService etudService;

@Autowired
EvaluationService evalService;
	
	//GET
			@GetMapping("/formations")
			@ResponseStatus(HttpStatus.OK)
			public List<Formation> getFormations(@RequestParam(name="id", required=false) Long id)
			{
				return service.getFormations(id);
			}
			
			@GetMapping("/formation/{id}/etudiants")
			@ResponseStatus(HttpStatus.OK)
			public List<Etudiant> getEtudiants(@PathVariable Long id)
			{
				return service.getFormations(id).get(0).getEtudiants();
			}
			
			@GetMapping("/formation/{id}/etudiantsNonInscrits")
			@ResponseStatus(HttpStatus.OK)
			public List<Etudiant> getEtudiantsNonInscrits(@PathVariable Long id)
			{
				Formation maFormation=this.service.getFormations(id).get(0);
				List <Etudiant> tousLesEtudiants=this.etudService.getEtudiants(null);
				List <Etudiant> etudiantInscrits=maFormation.getEtudiants();
				tousLesEtudiants.removeAll(etudiantInscrits);
				List <Etudiant> etudiantsNonInscrits=tousLesEtudiants;
				return etudiantsNonInscrits;

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
			
			@PostMapping("/formation/{id}/evaluation")
			@ResponseStatus(HttpStatus.CREATED)
			public void addEvaluation(@PathVariable Long id,@RequestBody Evaluation evaluation) throws IOException
			{	
				Formation formation=this.service.getFormations(id).get(0);
				evaluation.setFormation(formation);
				this.evalService.addEvaluation(evaluation);
				for(Question question:evaluation.getQuestions())
				{	
					question.setEvaluation(evaluation);
					this.questionRepository.save(question);
					for(Choix choix:question.getChoix())
					{   
						choix.setQuestion(question);
						this.choixRepository.save(choix);
					}
				}	
				formation.setEvaluation(evaluation);
				this.rep.save(formation);
				
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
