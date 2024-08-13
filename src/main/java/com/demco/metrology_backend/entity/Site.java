package com.demco.metrology_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@NamedQuery(name = "Site.getAllSite", query ="select s from Site s" )


@Entity
@Table(name = "sites")
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor

public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "numSite",nullable = false, unique = true)
    private String numSite;

    @Column(name = "nameSite", nullable = false)
    private String nameSite;

    @Column(name = "latitude",nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "hasReported", nullable = false)
    private boolean hasReported;

    @Column(name = "createdAt" , nullable = false)
    private Timestamp createdAt;

    @Column(name = "updatedAt",nullable = false)
    private Timestamp updatedAt;

  /*  @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<ParmMesure> reportings;*/

    /*@OneToMany(mappedBy = "site", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ParmMesure> reports;*/


}