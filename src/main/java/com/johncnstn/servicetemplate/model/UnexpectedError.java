package com.johncnstn.servicetemplate.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnexpectedError {
    private Integer code;

    private String message;
}
