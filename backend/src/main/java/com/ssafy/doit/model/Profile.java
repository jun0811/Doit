package com.ssafy.doit.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profilePk;

    private Long userPk;

    private String fileName;

//    @Column(name = "fileOriName")
    private String fileOriname;
    private String fileUrl;

}
