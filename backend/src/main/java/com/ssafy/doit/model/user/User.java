package com.ssafy.doit.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.doit.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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
    private String email;
    private String phone;
    private String nickname;

    @ColumnDefault("0")
    private int mileage;

    @Enumerated(EnumType.STRING)
    private UserRole user_role;

    @Builder
    public User(String email, String password, String nickname, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.user_role = userRole;
    }

    public String getRoleKey() {
        return this.user_role.getKey();
    }
}
