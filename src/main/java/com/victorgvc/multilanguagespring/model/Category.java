package com.victorgvc.multilanguagespring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "category", schema = "public")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    @Id
    @Column(name = "cat_id")
    private Integer id;

    @Column(name = "cat_name")
    private String name;

    @Column(name = "cat_description")
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "category")
    private List<Item> items;
}
