package com.ufrn.imd.ecommerce.services;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(String to, String subject, String text) {
        // Simulação de envio de e-mail
        System.out.println("Enviando e-mail para: " + to);
        System.out.println("Assunto: " + subject);
        System.out.println("Mensagem: " + text);
    }
}
