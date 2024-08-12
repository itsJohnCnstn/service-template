package com.johncnstn.servicetemplate.exception;

import java.util.function.Supplier;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtils {

    public static Supplier<NotFoundException> entityNotFound(String message) {
        return () -> new NotFoundException(message);
    }
}
