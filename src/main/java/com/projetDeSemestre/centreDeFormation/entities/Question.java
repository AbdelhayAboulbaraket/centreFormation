package com.projetDeSemestre.centreDeFormation.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name="QUESTION")
public class Question {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	String enonce;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="EVALUATION_QUESTION")
	Evaluation evaluation;
	
	
	@OneToMany(mappedBy="question",cascade=CascadeType.ALL)
	@Column(name="CHOIX_QUESTION")
	List<Choix> choix;
	
	String bonneReponse;
	
	Long numeroDeQuestion;

}
