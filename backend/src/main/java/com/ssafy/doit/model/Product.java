package com.ssafy.doit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.doit.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_pk")
    private Long id;

    private String category;
    private String title;
    private String content;
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_pk", insertable = false, updatable = false)
    private User user;
}
