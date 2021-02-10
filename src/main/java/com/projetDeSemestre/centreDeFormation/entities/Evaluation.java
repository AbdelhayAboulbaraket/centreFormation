package com.projetDeSemestre.centreDeFormation.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name="EVALUATION")
public class Evaluation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="FORMATION_EVALUATION")
	Formation formation;
	
	@OneToMany(mappedBy="evaluation",cascade=CascadeType.ALL)
	@Column(name="QUESTIONS_EVALUATION")
	List<Question> questions;
	
	@Column(name="DATE_EVALUATION")
	LocalDateTime date;

}
