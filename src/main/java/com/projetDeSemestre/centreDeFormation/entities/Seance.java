package com.projetDeSemestre.centreDeFormation.entities;




import java.time.LocalDateTime;
import java.util.List;

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
@Table(name="SEANCE")
public @Data class Seance {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@Column(name="DATE_DEBUT_SEANCE")
	LocalDateTime dateDebut;
	
	@Column(name="DATE_FIN_SEANCE")
	LocalDateTime dateFin;
	
	@Column(name="DESCRIPTION_SEANCE")
	String description;
	
	@ManyToOne
	@JoinColumn(name="FORMATION_SEANCE")
	Formation formation;
	
	
	@OneToMany(mappedBy="seance")
	@Column(name="SUPPORTS_SEANCE")
	List<Support> supports; 
	
	@OneToMany(mappedBy="seance")
	@Column(name="COMMENTAIRES_SEANCE")
	List<Commentaire> commentaires; 
	
	
}
