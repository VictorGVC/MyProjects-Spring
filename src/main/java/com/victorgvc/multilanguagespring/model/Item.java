package com.victorgvc.multilanguagespring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "category_item", schema = "public")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    
    @Id
    @Column(name = "it_id")
    private Integer id;

    @Column(name = "it_name")
    private String name;

    @Column(name = "it_image")
    private String image;
    
    @JoinColumn(name = "it_category")
    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(name = "project_item", 
        joinColumns = @JoinColumn(name = "pi_item"), 
        inverseJoinColumns = @JoinColumn(name = "pi_project"))
    private List<Project> projects;
}
