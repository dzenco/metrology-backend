package com.demco.metrology_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email =:email")
@NamedQuery(name = "User.getAllUser",query = "select new com.demco.metrology_backend.wrapper.UserWrapper(u.id, u.firstName, u.lastName,u.telephone, u.email, u.status) from  User u where u.role = 'user' ")
@NamedQuery(name = "User.updateStatus",query = "update User u set u.status =: status, u.updatedAt =: updatedAt where u.id =: id ")
@NamedQuery(name = "User.getAllAdmin",query = "select  u.email from  User u where u.role = 'admin' ")

@Entity
@Table(name = "users")
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String telephone;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String status;
    @Column(name = "createdAt" , nullable = false)
    private Timestamp createdAt;
    @Column(name = "updatedAt",nullable = false)
    private Timestamp updatedAt;

}
