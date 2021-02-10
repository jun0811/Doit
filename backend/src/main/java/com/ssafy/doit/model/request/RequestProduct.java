package com.ssafy.doit.model.request;

import jdk.nashorn.internal.runtime.Debug;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestProduct {

    private Long id;

    private String category;
    private String title;
    private String content;
    private String image;

    private int mileage;
    private boolean status;

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
