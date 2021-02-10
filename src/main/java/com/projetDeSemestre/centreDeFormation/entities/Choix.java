package com.projetDeSemestre.centreDeFormation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name="choix")
public class Choix {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="QUESTION_CHOIX")
	Question question;
	
	@Column(name="VALEUR_CHOIX")
	String valeurChoix;
	
}
