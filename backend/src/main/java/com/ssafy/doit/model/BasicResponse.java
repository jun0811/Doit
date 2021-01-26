package com.ssafy.doit.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    public boolean status;
    public String data;
    public Object object;
}
