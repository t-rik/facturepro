package com.pfa.facturation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@example.com}")
    private String fromEmail;

    @Value("${app.frontend-url:http://localhost:4200}")
    private String frontendUrl;

    public void sendVerificationEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("FacturePro - Vérifiez votre adresse email");
        message.setText(
            "Bienvenue sur FacturePro !\n\n" +
            "Cliquez sur le lien suivant pour vérifier votre adresse email :\n\n" +
            frontendUrl + "/verify-email?token=" + token + "\n\n" +
            "Ce lien expire dans 24 heures.\n\n" +
            "Si vous n'avez pas créé de compte, ignorez cet email.\n\n" +
            "L'équipe FacturePro"
        );
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("FacturePro - Réinitialisation du mot de passe");
        message.setText(
            "Vous avez demandé une réinitialisation de mot de passe.\n\n" +
            "Cliquez sur le lien suivant pour définir un nouveau mot de passe :\n\n" +
            frontendUrl + "/reset-password?token=" + token + "\n\n" +
            "Ce lien expire dans 1 heure.\n\n" +
            "Si vous n'avez pas demandé cette réinitialisation, ignorez cet email.\n\n" +
            "L'équipe FacturePro"
        );
        mailSender.send(message);
    }
}
