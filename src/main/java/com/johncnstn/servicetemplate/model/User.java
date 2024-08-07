package com.johncnstn.servicetemplate.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String description;
}
