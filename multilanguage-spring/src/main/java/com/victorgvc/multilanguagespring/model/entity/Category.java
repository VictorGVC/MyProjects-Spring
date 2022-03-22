package com.victorgvc.multilanguagespring.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "category")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    @Id
    @Column(name = "cat_id")
    private int id;

    @Column(name = "cat_name")
    private String name;

    @Column(name = "cat_description")
    private String description;

    @OneToMany(mappedBy = "category")
    @JoinColumn(name = "it_category") // todo: lembrar de testar
    private List<Project> projects;
}
