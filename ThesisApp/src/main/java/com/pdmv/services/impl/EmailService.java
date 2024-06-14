/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.dto.ThesisDetailsDTO;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    
    @Async
    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(variables);

            String htmlBody = templateEngine.process(templateName, thymeleafContext); 

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); 

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); 

            emailSender.send(message); 
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage()); 
        }
    }
    
    public void sendThesisNotificationEmail(ThesisDetailsDTO thesis, String recipientEmail, String recipientName, String role) {
        Map<String, Object> variables = new HashMap<>();
        
        variables.put("thesis", thesis);
        variables.put("recipientName", recipientName); 
        variables.put("role", role); 

        String subject = "Thông báo về khóa luận: " + thesis.getName(); 
        String templateName = "thesis-notification-email"; 

        sendEmail(recipientEmail, subject, templateName, variables);
    }
}
