package com.demco.metrology_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NamedQuery(name = "ParmMesure.getAllParmMesure", query ="select p from ParmMesure p" )
@NamedQuery(name = "ParmMesure.getParmMesureById", query ="select p from ParmMesure p where p.id =: id" )

@Entity
@Table(name = "parm_mesure")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate

public class ParmMesure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dateMesure;

    @Column(nullable = false)
    private float temperature;

    @Column(nullable = false)
    private float pression;

    @Column(nullable = false)
    private float debit;

    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Column(name = "createdAt" , nullable = false)
    private Timestamp createdAt;

    @Column(name = "updatedAt",nullable = false)
    private Timestamp updatedAt;
}
