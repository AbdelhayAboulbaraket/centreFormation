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
@Data
@Table(name="COMMENTAIRE")
public class Commentaire {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	@JoinColumn(name="PROPRIETAIRE_COMMENTAIRE")
	User proprietaire;
	
	
	@Column(name="CONTENU_COMMENTAIRE")
	String contenu;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="SEANCE_COMMENTAIRE")
	Seance seance;
	
	@Column(name="DATE_COMMENTAIRE")
	LocalDateTime date;
}
