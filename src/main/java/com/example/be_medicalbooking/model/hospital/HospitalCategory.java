package com.example.be_medicalbooking.model.hospital;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class HospitalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "hospitalCategory",cascade = CascadeType.ALL)
    private Set<Hospital> hospitals= new HashSet<>();
}
