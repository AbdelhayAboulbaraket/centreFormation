package com.projetDeSemestre.centreDeFormation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.projetDeSemestre.centreDeFormation.entities.Etudiant;
import com.projetDeSemestre.centreDeFormation.entities.Formation;
import com.projetDeSemestre.centreDeFormation.entities.Seance;


@Service
public class EmailService{
	
	
    public JavaMailSender emailSender;
    
    @Autowired
    public EmailService(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}



	public void sendInscriptionInformations(Etudiant etudiant,Formation formation) throws MailException
    {
        
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(etudiant.getEmail()); 
        message.setSubject("Inscription à une formation");
        
		String text="Bonjour monsieur "+etudiant.getNom()+", \n"
				+"Vous trouvez ci-dessous les créneaux de la formation "+formation.getTitre()+":\n";
		int i=1;
		for(Seance seance:formation.getSeances())
		{	
			text+="Seance numéro "+i+" : du "+seance.getCreneau()+" jusqu'à "+ seance.getDateFin()+".\n";
			i++;
		}
		text+="Cordialement";
        message.setText(text);
        emailSender.send(message);
        
    }

}