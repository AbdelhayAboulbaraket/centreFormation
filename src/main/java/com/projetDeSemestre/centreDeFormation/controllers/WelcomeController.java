package com.projetDeSemestre.centreDeFormation.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projetDeSemestre.centreDeFormation.entities.AuthRequest;
import com.projetDeSemestre.centreDeFormation.entities.JwtResponse;
import com.projetDeSemestre.centreDeFormation.entities.User;
import com.projetDeSemestre.centreDeFormation.repositories.UserRepository;
import com.projetDeSemestre.centreDeFormation.util.JwtUtil;
@CrossOrigin(origins = "*")
@RestController
public class WelcomeController {
	
	@Autowired
	UserRepository userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Centre de formation.";
    }

    @PostMapping("/authenticate")
    public JwtResponse generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        JwtResponse jwtresp=new JwtResponse();
		User user=this.userRepo.findByUsername(authRequest.getUsername());
        jwtresp.setJwt(jwtUtil.generateToken(authRequest.getUsername()));
        jwtresp.setUser(user);
        
        
        return jwtresp;
    }
}
