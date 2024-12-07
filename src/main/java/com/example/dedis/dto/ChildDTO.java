package com.example.dedis.dto;

import com.example.dedis.enums.Gender;
import com.example.dedis.enums.Grade;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ChildDTO {
    private ParentDTO parent;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private Integer enrollmentYear;
    private Grade grade;
    private Gender gender;
    private String address;
    private String postalCode;
    private String city;
}
