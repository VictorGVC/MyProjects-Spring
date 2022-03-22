package com.victorgvc.multilanguagespring.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "project")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    
    @Id
    @Column(name = "pro_id")
    private int id;

    @Column(name = "pro_name")
    private String name;

    @Column(name = "pro_description")
    private String description;

    @Column(name = "pro_readme")
    private String readme;

    @Column(name = "pro_link")
    private String link;

    @Column(name = "pro_user")
    @ManyToOne
    private User user;

    @ManyToMany(mappedBy = "projects")
    private List<Item> items;
}
