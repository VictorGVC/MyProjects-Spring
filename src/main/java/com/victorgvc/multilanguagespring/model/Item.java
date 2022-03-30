package com.victorgvc.multilanguagespring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "category_item", schema = "public")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "it_id")
    private Integer id;

    @Column(name = "it_name")
    private String name;

    @Column(name = "it_image")
    private String image;
    
    @JsonBackReference
    @JoinColumn(name = "it_category")
    @ManyToOne
    private Category category;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "project_item", 
        joinColumns = @JoinColumn(name = "pi_item"), 
        inverseJoinColumns = @JoinColumn(name = "pi_project"))
    private List<Project> projects;
}
