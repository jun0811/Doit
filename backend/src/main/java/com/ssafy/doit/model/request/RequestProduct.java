package com.ssafy.doit.model.request;

import jdk.nashorn.internal.runtime.Debug;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestProduct {

    private Long id;

    private String category;
    private String title;
    private String content;
    private String image;

    private int mileage;
    private boolean status;

    public RequestProduct toEntity(){
        RequestProduct build = RequestProduct.builder()
                .id(id)
                .category(category)
                .title(title)
                .content(content)
                .image(image).build();
        return build;
    }
    @Builder
    public RequestProduct(Long id, String category, String title, String content,String image, int mileage,boolean status) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.content = content;
        this.image = image;
        this.mileage = mileage;
        this.status = true;
    }
}
