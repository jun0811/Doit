package com.ssafy.doit.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.doit.model.GroupUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    private String email;
    private String nickname;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String authKey;
    private String image;
    private int mileage;
    private String feedOpen;
    private String groupOpen;
    private String status;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public List<GroupUser> groupList; // 그룹리스트

    @Builder
    public User(String email, String password, String nickname,String image, UserRole userRole, String authKey) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.image = image;
        this.userRole = userRole;
        this.authKey = authKey;
        this.mileage = 0;
        this.feedOpen = "true";
        this.groupOpen = "true";
        this.status = "true";
    }
    public String getRoleKey() {
        return this.userRole.getKey();
    }
}
