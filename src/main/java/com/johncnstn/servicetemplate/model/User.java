package com.johncnstn.servicetemplate.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String description;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime deletedAt;
}
