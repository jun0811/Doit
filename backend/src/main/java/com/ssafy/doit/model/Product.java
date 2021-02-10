package com.ssafy.doit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.doit.model.chat.ChatRoom;
import com.ssafy.doit.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_pk")
    private Long id;

    private String category;
    private String title;
    private String content;
    private String image;

    private int mileage;
    private boolean status;
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "user_pk")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ChatRoom> chatRooms;

    @PrePersist
    private void createAt(){
        this.createDate = LocalDateTime.now();
    }
}