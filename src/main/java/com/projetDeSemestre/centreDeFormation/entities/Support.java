package com.projetDeSemestre.centreDeFormation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="SUPPORT")
public class Support {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	@JoinColumn(name="SEANCE_SUPPORT")
	Seance seance;
	
	@Column(name="SUPPORT_PATH",nullable = true, length = 255)
    private String path;
	

}
