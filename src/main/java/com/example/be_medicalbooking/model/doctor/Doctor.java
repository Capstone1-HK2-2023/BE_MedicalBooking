package com.example.be_medicalbooking.model.doctor;

import com.example.be_medicalbooking.model.appointment.InformationService;
import com.example.be_medicalbooking.model.user.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "doctors",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private Date birthday;
    private boolean gender;
    @Lob
    private String description;
    @Lob
    private String image_url;
    private boolean status;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "doctor_education",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "education_id"))
    private Set<Education> educations = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "doctor_position",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id"))
    private Set<Position> positions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "doctor")
    private Set<InformationService> informationServices= new HashSet<>();
}
