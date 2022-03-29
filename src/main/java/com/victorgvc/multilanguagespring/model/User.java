package com.victorgvc.multilanguagespring.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user", schema = "public")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "us_id")
    private Integer id;

    @Column(name = "us_username")
    private String username;

    @Column(name = "us_password")
    private String password;

    @Column(name = "us_admin")
    private Boolean admin = false;

    @Column(name = "us_github")
    private String github;

    @Column(name = "us_linkedin")
    private String linkedin;

    @Column(name = "us_photo")
    private String photo;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", orphanRemoval=true,cascade = CascadeType.PERSIST)
    private List<Project> projects;

    @Transient
    private String confirmPassword;
}
