package com.projetDeSemestre.centreDeFormation.services;

import javax.mail.internet.InternetAddress;

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
		
		if(formation.getSeances()!=null)
		{
		for(Seance seance:formation.getSeances())
		{	
			text+="Seance numéro "+i+" : du "+seance.getCreneau()+" jusqu'à "+ seance.getDateFin()+".\n";
			i++;
		}
		}
		text+="Cordialement";
        message.setText(text);
        emailSender.send(message);
        
    }
	
//	public void sendAfterFileAdd(String link,Formation formation) throws MailException
//    {	
//		String[] mails=new String[] {};
//        SimpleMailMessage message = new SimpleMailMessage();
//        int i=0;
//        for(Etudiant etud:formation.getEtudiants())
//        {	
//        	mails[i]=etud.getEmail();
//        }
//        //String[] myMails=(String[])mails.toArray();
//        message.setTo(mails);
//        //message.setTo(etudiant.getEmail()); 
//        message.setSubject("Un nouveau support de cours Ajouté par votre professeur");
//        
//		String text="Bonjour, \n"
//				+"Un nouveau fichier/cours est ajouté à la formation "+formation.getTitre()+".\n"
//				+"Vous pouvez le consulter sur: "+link;
//		text+="Cordialement";
//        message.setText(text);
//        //emailSender.send(message);
//        
//    }
//	
//	public void sendAfterZoomGenerated(Seance seance,Formation formation) throws MailException
//    {	
//		String mails="";
//        SimpleMailMessage message = new SimpleMailMessage();
//        int i=0;
//        for(Etudiant etud:formation.getEtudiants())
//        {	if(mails=="") mails+=etud.getEmail();
//
//        else mails+=","+etud.getEmail();
//        }
//        mails+="abdelhay.aboulbaraket@gmail.com,anouaraitabdelmalek@gmail.com";
//        message.setTo(to);
//        message.setSubject("Réunion");
//        
//		String text="Bonjour, \n"
//				+"La réunion de la séance "+seance.getTitre()+" va commencer.\n"
//				+"Vous pouvez la rejoindre en cliquant sur: "+seance.getPath();
//		text+="Cordialement";
//        message.setText(text);
//        emailSender.send(message);
//        
//    }

}