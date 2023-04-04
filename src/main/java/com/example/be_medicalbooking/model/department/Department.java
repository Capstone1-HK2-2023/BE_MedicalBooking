package com.example.be_medicalbooking.model.department;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String introduction;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "department_certificate",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "cartificate_id"))
    private Set<Certificate> certificates = new HashSet<>();
    private String status;

}
