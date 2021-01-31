package com.projetDeSemestre.centreDeFormation.entities;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="INTERVENANT")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ID_INTERVENANT")),
    @AttributeOverride(name = "nom", column = @Column(name = "NOM_INTERVENANT")),
    @AttributeOverride(name = "prenom", column = @Column(name = "PRENOM_INTERVENANT")),
    @AttributeOverride(name = "cin", column = @Column(name = "CIN_INTERVENANT")),
    @AttributeOverride(name = "adresse", column = @Column(name = "ADRESSE_INTERVENANT")),
    @AttributeOverride(name = "telephone", column = @Column(name = "TELEPHONE_INTERVENANT")),
    @AttributeOverride(name = "email", column = @Column(name = "EMAIL_INTERVENANT")),
    @AttributeOverride(name = "username", column = @Column(name = "USERNAME_INTERVENANT")),
    @AttributeOverride(name = "password", column = @Column(name = "PASSWORD_INTERVENANT")),
    @AttributeOverride(name = "role", column = @Column(name = "ROLE_INTERVENANT"))
})
public @Data class Intervenant extends User {
	@Column(nullable = true, length = 255)
    private String photo;
	
	@JsonIgnore
	@OneToMany(mappedBy="intervenant")
	@Column(name="FORMATIONS_INTERVENANT")
	List<Formation> formations;
}
