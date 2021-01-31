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
@Table(name="FORMATION")
public @Data class Formation {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		Long id;
		
		@Column(name="TITRE_FORMATION")
		String titre;
		
		@Column(name="DESCRIPTION_FORMATION")
		String description;
		
		@Column(name="PRIX_FORMATION")
		Double prix;
		
		@JoinColumn(name="CATEGORIE_FORMATION")
		@ManyToOne
		Categorie categorie;
		
		@JsonIgnore
		@Column(name="SEANCES_FORMATION")
		@OneToMany(mappedBy="formation")
		List<Seance> seances;
		
//		@Column(name="COUPONS_FORMATION")
//		@OneToMany(mappedBy="formation",cascade=CascadeType.ALL)
//		List<Coupon> coupons;
		
		@JsonIgnore
		@Column(name="ETUDIANTS_FORMATION")
		@OneToMany(mappedBy = "formations")
		List<Etudiant> etudiants;
		
		@ManyToOne
		@JoinColumn(name="CREATION_ADMIN_FORMATION")
		Admin creationAdmin;
		
		@ManyToOne
		@JoinColumn(name="INTERVENANT_FORMATION")
		Intervenant intervenant;
		
		@Column(nullable = true, length = 256)
	    private String thumbnail;
		
		
		
}
