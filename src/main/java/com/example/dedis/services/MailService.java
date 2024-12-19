package com.example.dedis.services;

import com.example.dedis.entities.Parent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${application.admin.email}")
    private String ADMIN_EMAIL;

    private final JavaMailSender mailSender;

    @SneakyThrows
    public void sendConfirmationEmail(String email, String childFullName) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(ADMIN_EMAIL);
        helper.setTo(email);
        helper.setSubject("Successful Registration Confirmation");
        helper.setText(
                "<html>" +
                        "<body>" +
                        "<p>Dear Parent,</p>" +
                        "<p>We are pleased to inform you that your child " + childFullName + " has been successfully registered in our school system.</p>" +
                        "<p>You will receive notifications about upcoming school activities, events, and important updates.</p>" +
                        "<p>Thank you for choosing <strong>Montessori School</strong> to support your child's educational journey.</p>" +
                        "<br>" +
                        "<p>Yours sincerely,</p>" +
                        "<p><strong>The Montessori School Team</strong></p>" +
                        "<img src='https://dedis.s3.eu-north-1.amazonaws.com/Montessori_logo.png' alt='Montessori Logo' style='width:150px;height:auto;'>" +
                        "</body>" +
                        "</html>",
                true
        );
        mailSender.send(message);
    }

    public void sendNotificationToAllParents(String eventTitle,List<Parent> parents){
        parents.forEach(parent -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(ADMIN_EMAIL);
                helper.setTo(parent.getEmail());
                helper.setSubject(eventTitle);
                // Email Body
                helper.setText(
                        "<html>" +
                                "<body>" +
                                "<p>Dear Parents,</p>" +
                                "<p>Montessori School is preparing a new event :</p>" +
                                "<h3>" + eventTitle + "</h3>" +
                                "<p>To learn more about this and other upcoming events, please visit our webpage:</p>" +
                                "<p><a href='https://www.yourschoolwebpage.com/events'>Check Our Events</a></p>" +
                                "<br>" +
                                "<p>Thank you for being a part of <strong>Montessori School</strong>!</p>" +
                                "<br>" +
                                "<p>Yours sincerely,</p>" +
                                "<p><strong>The Montessori School Team</strong></p>" +
                                "<img src='https://dedis.s3.eu-north-1.amazonaws.com/Montessori_logo.png' " +
                                "alt='Montessori Logo' style='width:150px;height:auto;'>" +
                                "</body>" +
                                "</html>",
                        true // Enable HTML content
                );
                mailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendCancelEventMessageToParents(String eventTitle, List<Parent> parents) {
        parents.forEach(parent -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(ADMIN_EMAIL);
                helper.setTo(parent.getEmail());
                helper.setSubject(eventTitle);
                helper.setText(
                "<html>" +
                        "<body>" +
                        "<p>Dear Parents,</p>" +
                        "<p>We regret to inform you that the following event has been <strong style='color:red;'>cancelled</strong>:</p>" +
                        "<h3>" + eventTitle + "</h3>" +
                        "<p>We apologize for any inconvenience this may cause and appreciate your understanding.</p>" +
                        "<p>For updates on other upcoming events, please visit our webpage:</p>" +
                        "<p><a href='https://www.yourschoolwebpage.com/events'>Check Our Events</a></p>" +
                        "<br>" +
                        "<p>Thank you for being a part of <strong>Montessori School</strong>!</p>" +
                        "<br>" +
                        "<p>Yours sincerely,</p>" +
                        "<p><strong>The Montessori School Team</strong></p>" +
                        "<img src='https://dedis.s3.eu-north-1.amazonaws.com/Montessori_logo.png' " +
                        "alt='Montessori Logo' style='width:150px;height:auto;'>" +
                        "</body>" +
                        "</html>",
                true // Enable HTML content
                );
                mailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}