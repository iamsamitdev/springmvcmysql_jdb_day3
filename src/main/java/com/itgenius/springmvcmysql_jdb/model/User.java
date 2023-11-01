package com.itgenius.springmvcmysql_jdb.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "demousers")
@EntityListeners(AuditingEntityListener.class)
public class User {
    
    // Constructor
    public User() {
        // Initial Value
    }

    // Create Field
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 5)
    private int id;

    // First Name
    @Column(name = "firtname", length = 64)
    @Size(min = 3, max = 64)
    @NotBlank(message = "Please enter firstname")
    private String firstname;

    // Last Name
    @Column(name = "lastname", length = 64)
    @Size(min = 3, max = 64)
    @NotBlank(message = "Please enter lastname")
    private String lastname;

    // Email
    @Column(name = "email", length = 64)
    @NotBlank(message = "Please enter email")
    @Email(message = "Please enter valid email")
    private String email;

    // Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
