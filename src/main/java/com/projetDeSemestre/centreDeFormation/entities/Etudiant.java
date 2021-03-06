package com.projetDeSemestre.centreDeFormation.entities;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="ETUDIANT")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ID_ETUDIANT")),
    @AttributeOverride(name = "nom", column = @Column(name = "NOM_ETUDIANT")),
    @AttributeOverride(name = "prenom", column = @Column(name = "PRENOM_ETUDIANT")),
    @AttributeOverride(name = "cin", column = @Column(name = "CIN_ETUDIANT")),
    @AttributeOverride(name = "adresse", column = @Column(name = "ADRESSE_ETUDIANT")),
    @AttributeOverride(name = "telephone", column = @Column(name = "TELEPHONE_ETUDIANT")),
    @AttributeOverride(name = "email", column = @Column(name = "EMAIL_ETUDIANT")),
    @AttributeOverride(name = "username", column = @Column(name = "USERNAME_ETUDIANT")),
    @AttributeOverride(name = "password", column = @Column(name = "PASSWORD_ETUDIANT")),
    @AttributeOverride(name = "role", column = @Column(name = "ROLE_ETUDIANT"))
})
public @Data class Etudiant extends User {
	
	
	@ManyToMany(fetch = FetchType.LAZY,
    cascade = {
        CascadeType.MERGE
    })
	@JoinTable(name = "INSCRIPTION_FORMATION",
    joinColumns = { @JoinColumn(name = "etudiant_id") },
    inverseJoinColumns = { @JoinColumn(name = "formation_id") })
	@JsonIgnore
    List<Formation> formations;
	
	@Column(nullable = true, length = 255)
    private String image;
	
}
