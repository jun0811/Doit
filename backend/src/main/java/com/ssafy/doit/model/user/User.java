package com.ssafy.doit.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_pk")
    private Long id;

    private String password;
    private String authKey;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @ColumnDefault("0")
    private int mileage;

    private String image;

    @Enumerated(EnumType.STRING)
    private UserRole user_role;


    @Builder
    public User(String email, String password, String nickname, UserRole userRole, String authKey) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.user_role = userRole;
        this.authKey = authKey;
    }

    public String getRoleKey() {
        return this.user_role.getKey();
    }

}
