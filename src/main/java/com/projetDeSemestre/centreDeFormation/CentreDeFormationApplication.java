package com.projetDeSemestre.centreDeFormation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projetDeSemestre.centreDeFormation.entities.Admin;
import com.projetDeSemestre.centreDeFormation.exceptions.NotFoundException;
import com.projetDeSemestre.centreDeFormation.services.AdminService;

@SpringBootApplication
public class CentreDeFormationApplication {
	
	@Autowired
    private AdminService service;
	
	
	@PostConstruct
    public void initUsers() {
	List<Admin>  currentAdminList= new ArrayList<Admin>();
	try {
	currentAdminList = service.getAdmins(null);	
	} catch (NotFoundException e) {
		Admin firstAdmin=new Admin();
		firstAdmin.setUsername("admin");
		firstAdmin.setPassword("admin");
		firstAdmin.setRole("Admin");
        service.addAdmin(firstAdmin);	
	}
		
    }
	
	public static void main(String[] args) {
		
		SpringApplication.run(CentreDeFormationApplication.class, args);
	}

}
