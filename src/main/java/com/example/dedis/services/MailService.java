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

    @Value("${application.frontend.reset-password-link}")
    private String FRONTEND_RESET_PASSWORD_LINK;

    @Value("${application.frontend.events-link}")
    private String EVENTS_LINK;

    private final JavaMailSender mailSender;

    @SneakyThrows
    public void sendConfirmationEmail(String email, String childFullName) {

        constructEmail(
                "Successful Registration Confirmation",
                email,
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
                "</html>"
        );
    }

    public void sendNotificationToAllParents(String eventTitle,List<Parent> parents){

        parents.forEach(parent -> constructEmail(
                eventTitle,
                parent.getEmail(),
                "<html>" +
                "<body>" +
                "<p>Dear Parents,</p>" +
                "<p>Montessori School is preparing a new event :</p>" +
                "<h3>" + eventTitle + "</h3>" +
                "<p>To learn more about this and other upcoming events, please visit our webpage:</p>" +
                "<p><a href='"+EVENTS_LINK+"'>Check Our Events</a></p>" +
                "<br>" +
                "<p>Thank you for being a part of <strong>Montessori School</strong>!</p>" +
                "<br>" +
                "<p>Yours sincerely,</p>" +
                "<p><strong>The Montessori School Team</strong></p>" +
                "<img src='https://dedis.s3.eu-north-1.amazonaws.com/Montessori_logo.png' " +
                "alt='Montessori Logo' style='width:150px;height:auto;'>" +
                "</body>" +
                "</html>"));

    }

    public void sendCancelEventMessageToParents(String eventTitle, List<Parent> parents) {

        parents.forEach(parent -> constructEmail(
                eventTitle,
                parent.getEmail(),
                "<html>" +
                        "<body>" +
                        "<p>Dear Parents,</p>" +
                        "<p>We regret to inform you that the following event has been <strong style='color:red;'>cancelled</strong>:</p>" +
                        "<h3>" + eventTitle + "</h3>" +
                        "<p>We apologize for any inconvenience this may cause and appreciate your understanding.</p>" +
                        "<p>For updates on other upcoming events, please visit our webpage:</p>" +
                        "<p><a href='"+EVENTS_LINK+"'>Check Our Events</a></p>" +
                        "<br>" +
                        "<p>Thank you for being a part of <strong>Montessori School</strong>!</p>" +
                        "<br>" +
                        "<p>Yours sincerely,</p>" +
                        "<p><strong>The Montessori School Team</strong></p>" +
                        "<img src='https://dedis.s3.eu-north-1.amazonaws.com/Montessori_logo.png' " +
                        "alt='Montessori Logo' style='width:150px;height:auto;'>" +
                        "</body>" +
                        "</html>"));

    }

    public void sendResetPasswordMail(String resetCode) {

        constructEmail(
                "Reset password",
                ADMIN_EMAIL,
                 "<html>" +
                "<body>" +
                "<p>Dear Administrator,</p>" +
                "<p>You have requested a password reset. Please use the following one-time code to reset your password:</p>" +
                         "<h2 style='color:blue;'>" + resetCode + "</h2>" +
                         "<p>To proceed with resetting your password, please click the link below:</p>" +
                         "<p><a href='" + FRONTEND_RESET_PASSWORD_LINK + "' style='font-size:16px; color:#007BFF; text-decoration:none;'>" +
                         "Reset Your Password</a></p>" +
                "<p>This code is valid for a limited time. If you did not request a password reset, please disregard this message.</p>" +
                "<br>" +
                "<p>For any further assistance, feel free to contact the support team.</p>" +
                "<br>" +
                "<p>Yours sincerely,</p>" +
                "<p><strong>The Support Team</strong></p>" +
                "<img src='https://dedis.s3.eu-north-1.amazonaws.com/Montessori_logo.png' " +
                "alt='Montessori Logo' style='width:150px;height:auto;'>" +
                "</body>" +
                "</html>"
        );
    }

    private void constructEmail(String eventTitle,String emailSendingTo,String body) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(ADMIN_EMAIL);
            helper.setTo(emailSendingTo);
            helper.setSubject(eventTitle);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}