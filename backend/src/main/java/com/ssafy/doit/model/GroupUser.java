package com.ssafy.doit.model;

import com.ssafy.doit.model.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`group_has_user`")
public class GroupUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_pk")
    @NonNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_pk")
    @NonNull
    private Group group;
}
