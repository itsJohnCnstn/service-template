package com.johncnstn.servicetemplate.exception;

import java.net.URI;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("PMD")
public class ErrorConstants {

    private static final String BASE_URL = "/problem";

    static URI DEFAULT = URI.create(BASE_URL + "/default");
    static URI CONSTRAINT_VIOLATION = URI.create(BASE_URL + "/constraint-violation");
    static URI BAD_REQUEST = URI.create(BASE_URL + "/bad-request");
    static URI CONFLICT = URI.create(BASE_URL + "/conflict");
    static URI NOT_FOUND = URI.create(BASE_URL + "/not-found");
    static URI UNAUTHORIZED = URI.create(BASE_URL + "/unauthorized");
    static URI FORBIDDEN = URI.create(BASE_URL + "/forbidden");
    static URI UNPROCESSABLE_ENTITY = URI.create(BASE_URL + "/unprocessable-entity");
    static URI SERVICE_UNAVAILABLE = URI.create(BASE_URL + "/service-unavailable");
}
