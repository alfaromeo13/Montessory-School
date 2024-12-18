package com.example.dedis.services;

import com.example.dedis.dto.ChildDTO;
import com.example.dedis.entities.Child;
import com.example.dedis.entities.Parent;
import com.example.dedis.mappers.ChildMapper;
import com.example.dedis.repositories.ChildRepository;
import com.example.dedis.repositories.ParentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final MailService mailSender;
    private final ChildMapper childMapper;
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
        mailSender.sendConfirmationEmail(parent.getEmail(),child.getName()+" "+child.getSurname());
        childRepository.save(child);
    }
}