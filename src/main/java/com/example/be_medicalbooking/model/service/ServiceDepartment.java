package com.example.be_medicalbooking.model.service;

import com.example.be_medicalbooking.model.appointment.InformationService;
import com.example.be_medicalbooking.model.department.Department;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "service_id", "department_id" })
        }
)
public class ServiceDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Service service;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
    private double price;
    @Lob
    private String description;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "serviceDepartment")
    private Set<InformationService> informationServices= new HashSet<>();
    private int status;
}
