package com.example.be_medicalbooking.model.appointment;

import com.example.be_medicalbooking.model.doctor.Doctor;
import com.example.be_medicalbooking.model.hospital.Hospital;
import com.example.be_medicalbooking.model.service.ServiceDepartment;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class InformationService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", referencedColumnName = "id")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceDepartment_id", referencedColumnName = "id")
    private ServiceDepartment serviceDepartment;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "informationService")
    private Set<Appointment> appointments= new HashSet<>();
}
