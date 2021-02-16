package com.projetDeSemestre.centreDeFormation.entities;

import lombok.Data;

@Data
public class JwtResponse {
	String jwt;
	User user;
}
