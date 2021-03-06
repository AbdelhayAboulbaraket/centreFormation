package com.projetDeSemestre.centreDeFormation.entities;

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
@Table(name="CATEGORIE")
public @Data class Categorie {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;

	
	@Column(name="NOM_CATEGORIE")
	String designation;
	

	
	@JsonIgnore
	@OneToMany(mappedBy="categorie")
	@Column(name="CLIENTS_AGENCE")
	List<Formation> formations;
}
