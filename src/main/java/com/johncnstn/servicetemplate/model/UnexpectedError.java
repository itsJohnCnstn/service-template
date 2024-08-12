package com.johncnstn.servicetemplate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UnexpectedError {
    private Integer code;

    private String message;
}
