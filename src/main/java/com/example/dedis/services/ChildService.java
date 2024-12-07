package com.example.dedis.services;

import com.example.dedis.dto.ChildDTO;
import com.example.dedis.entities.Child;
import com.example.dedis.entities.Parent;
import com.example.dedis.mappers.ChildMapper;
import com.example.dedis.repositories.ChildRepository;
import com.example.dedis.repositories.ParentRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildMapper childMapper;
    private final JavaMailSender mailSender;
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;

    public List<Child> findAll() {
        return childRepository.findAll();
    }

    @Transactional
    public void registerChild(ChildDTO childDTO) {
        Child child = childMapper.toEntity(childDTO);

        // Check if the parent exists; if not, save it
        Parent parent = parentRepository.findByEmail(child.getParent().getEmail())
                .orElseGet(() -> parentRepository.save(child.getParent()));

        child.setParent(parent);
        sendConfirmationEmail(parent.getEmail(),child.getName()+" "+child.getSurname());
        childRepository.save(child);
    }

    @SneakyThrows
    private void sendConfirmationEmail(String email, String childFullName) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("jovanvukovic09@gmail.com");
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
}