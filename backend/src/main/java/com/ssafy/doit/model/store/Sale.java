package com.ssafy.doit.model.store;

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
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_pk")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_pk")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_pk")
    private User user;
}
