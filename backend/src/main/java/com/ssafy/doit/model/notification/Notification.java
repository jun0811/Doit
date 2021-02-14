package com.ssafy.doit.model.notification;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_pk")
    private Long id;

    private Long userPk;
    private NotiType notiType;
    private Long targetId;
    private boolean status;
    private LocalDateTime notiDate;
    private String desc;

    @PrePersist
    private void createAt(){
        this.notiDate = LocalDateTime.now();
    }
}
