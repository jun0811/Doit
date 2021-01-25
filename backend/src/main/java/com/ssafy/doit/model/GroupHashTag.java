package com.ssafy.doit.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`group_has_tag`")
public class GroupHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_pk")
    @NonNull
    private HashTag hashTag;

    @ManyToOne
    @JoinColumn(name = "group_pk")
    @NonNull
    private Group group;

//    public boolean match(String name) {
//        return this.hashTag.getName().equals(name);
//    }
}