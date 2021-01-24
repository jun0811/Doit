package com.ssafy.doit.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GroupHasTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_pk")
    @NonNull
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "group_pk")
    @NonNull
    private Group group;

    public boolean match(String word) {
        return this.tag.getKeyword().equals(word);
    }
}