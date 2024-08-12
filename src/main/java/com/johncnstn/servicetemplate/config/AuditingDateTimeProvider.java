package com.johncnstn.servicetemplate.config;

import static java.time.LocalDateTime.now;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

@Component
public class AuditingDateTimeProvider implements DateTimeProvider {

    @NotNull @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(now().withNano(0));
    }
}
