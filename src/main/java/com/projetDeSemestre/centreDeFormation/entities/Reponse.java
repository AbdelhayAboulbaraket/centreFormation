package com.projetDeSemestre.centreDeFormation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="REPONSE")
public class Reponse {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	
	@JsonIgnore
	@ManyToOne
	PassageEvaluation passageEvaluation;
	
	@Column(name="VALEUR")
	String valeur;
	
	@Column(name="numeroQuestion")
	Long numeroDeQuestion;
	
	
	
	
}
