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

import com.projetDeSemestre.centreDeFormation.entities.Admin;
import com.projetDeSemestre.centreDeFormation.exceptions.AlreadyExistsException;
import com.projetDeSemestre.centreDeFormation.services.AdminService;
import com.projetDeSemestre.centreDeFormation.util.FileUploadUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api")
public class AdminController {
	
	
	AdminService service;
	
	@Autowired
	public AdminController(AdminService service) {
		
		this.service=service;
	}
	
	//GET
			@GetMapping("/admins")
			@ResponseStatus(HttpStatus.OK)
			public List<Admin> getAdmins(@RequestParam(name="id", required=false) Long id)
			{
				return service.getAdmins(id);
			}
			
			
			@GetMapping("/admin/username/{username}")
			@ResponseStatus(HttpStatus.OK)
			public Admin getByUsername(@PathVariable(name="username") String username)
			{
				return service.getByUsername(username);
			}
			
			

		
		//POST
			
			@PostMapping("/admins")
			@ResponseStatus(HttpStatus.CREATED)
			public Admin addAdmin(@RequestBody Admin admin) throws IOException,AlreadyExistsException
			{	
				Admin adminToReturn=service.addAdmin(admin);
				return adminToReturn;
			}
			
			@PostMapping("/adminPicture/{id}")
			@ResponseStatus(HttpStatus.CREATED)
			//requestParams@RequestBody Admin admin,@RequestBody MultipartFile multipartFile
			public void addAdminPicture(@PathVariable Long id,@RequestParam("image") MultipartFile multipartFile ) throws IOException,AlreadyExistsException
			{	
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
					
				//Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
				Admin admin=service.getAdmins(id).get(0);
				
				String nomPhoto=admin.getId()+"."+fileName.split("[.]")[1];
				String uploadDir = "src/main/resources/public/photosDeProfil/";
				admin.setImage("http://localhost:8081/photosDeProfil/"+nomPhoto);
				service.updateAdmin(id, admin);

		        FileUploadUtil.saveFile(uploadDir, nomPhoto, multipartFile);
		        // à Refaire.
		        // edit : c'est mieux comme ça. 
			}
		
		
		
		//PUT
			
			@PutMapping("/admin/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void updateAdmin(@PathVariable Long id , @RequestBody(required=false) Admin admin)
			{	
				service.updateAdmin(id,admin);
			}
	
		
			
		//DELETE
			
			@DeleteMapping("/admin/{id}")
			@ResponseStatus(HttpStatus.OK)
			public void deleteAdmin(@PathVariable Long id)
			{
				service.removeAdmin(id);
			}
			
	

}

