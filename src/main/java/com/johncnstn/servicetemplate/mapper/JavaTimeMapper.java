package com.johncnstn.servicetemplate.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.mapstruct.Mapper;

@Mapper
public interface JavaTimeMapper {

    JavaTimeMapper JAVA_TIME_MAPPER = getMapper(JavaTimeMapper.class);

    default OffsetDateTime toOffsetDateTime(LocalDate source) {
        return source == null ? null : OffsetDateTime.from(source.atStartOfDay(ZoneOffset.UTC));
    }

    default OffsetDateTime toOffsetDateTime(Instant source) {
        return source == null ? null : OffsetDateTime.ofInstant(source, ZoneOffset.UTC);
    }

    default LocalDate toLocalDate(OffsetDateTime source) {
        return source == null ? null : LocalDate.from(source);
    }

    default LocalDate toLocalDate(Instant source) {
        return source == null ? null : LocalDate.ofInstant(source, ZoneOffset.UTC);
    }

    default Instant toInstant(OffsetDateTime source) {
        return source == null ? null : Instant.from(source);
    }

    default Instant toInstant(LocalDate source) {
        return source == null ? null : Instant.from(source.atStartOfDay(ZoneOffset.UTC));
    }
}
