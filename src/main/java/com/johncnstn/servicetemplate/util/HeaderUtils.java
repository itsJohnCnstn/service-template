package com.johncnstn.servicetemplate.util;

import static com.johncnstn.servicetemplate.util.LinkUtils.buildPaginationLink;
import static com.johncnstn.servicetemplate.util.PathUtils.generatePath;
import static org.springframework.http.HttpHeaders.LINK;
import static org.springframework.http.HttpHeaders.LOCATION;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

@UtilityClass
public final class HeaderUtils {

    private static final String X_TOTAL_COUNT = "X-Total-Count";

    @SneakyThrows
    public static HttpHeaders generateLocationHeader(String baseUrl, Object... pathVariables) {
        var headers = new HttpHeaders();
        headers.add(LOCATION, generatePath(baseUrl, pathVariables));
        return headers;
    }

    public static <T> HttpHeaders generatePaginationHeaders(
            Page<T> page, String baseUrl, Object... pathVariables) {
        var methodPath = generatePath(baseUrl, pathVariables);
        var link = buildPaginationLink(page, methodPath);
        return buildHeaders(page, link);
    }

    @NotNull private static <T> HttpHeaders buildHeaders(Page<T> page, String link) {
        var headers = new HttpHeaders();
        headers.add(LINK, link);
        headers.add(X_TOTAL_COUNT, Long.toString(page.getTotalElements()));
        return headers;
    }
}
