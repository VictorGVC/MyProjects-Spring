package com.victorgvc.multilanguagespring.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "us_id")
    private int id;

    @Column(name = "us_username")
    private String username;

    @Column(name = "us_password")
    private String password;

    @Column(name = "us_admin")
    private boolean admin;

    @Column(name = "us_github")
    private String github;

    @Column(name = "us_linkedin")
    private String linkedin;

    @Column(name = "us_photo")
    private String photo;
}
