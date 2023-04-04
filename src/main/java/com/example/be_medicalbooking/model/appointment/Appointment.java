package com.example.be_medicalbooking.model.appointment;

import com.example.be_medicalbooking.model.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.print.attribute.DateTimeSyntax;
import java.time.LocalDateTime;

@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "codeAppointment")
        }
)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String codeAppointment;
    private String name;
    private String phone;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "informationService_id")
    private InformationService informationService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private int quantity;
    private LocalDateTime dateCreate;
    private LocalDateTime dateAppointment;
    @Lob
    private String note;
    private int updaterStatus;
    private int status;

}
