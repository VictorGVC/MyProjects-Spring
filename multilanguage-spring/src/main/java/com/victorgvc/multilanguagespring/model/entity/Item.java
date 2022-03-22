package com.victorgvc.multilanguagespring.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "category_item")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    
    @Id
    @Column(name = "it_id")
    private int id;

    @Column(name = "it_name")
    private String name;

    @Column(name = "it_image")
    private String image;
    
    @Column(name = "it_category")
    @ManyToOne
    private Category category;
}
