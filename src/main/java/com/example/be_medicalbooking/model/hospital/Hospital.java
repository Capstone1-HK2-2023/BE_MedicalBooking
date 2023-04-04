package com.example.be_medicalbooking.model.hospital;

import com.example.be_medicalbooking.model.appointment.InformationService;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hopitalCategory_id",referencedColumnName = "id")
    private HospitalCategory hospitalCategory;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "hospital")
    private Set<InformationService> informationServices= new HashSet<>();
    private boolean status;
}
