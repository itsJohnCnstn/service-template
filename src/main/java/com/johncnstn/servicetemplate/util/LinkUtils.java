package com.johncnstn.servicetemplate.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

@UtilityClass
public class LinkUtils {

    private static final String LINK_PREFIX = "<";
    private static final String FIRST_LINK_POSTFIX = ">; rel=\"first\", ";
    private static final String PREV_LINK_POSTFIX = ">; rel=\"prev\", ";
    private static final String NEXT_LINK_POSTFIX = ">; rel=\"next\", ";
    private static final String LAST_LINK_POSTFIX = ">; rel=\"last\"";
    private static final String PAGE_QUERY_PARAM_NAME = "page";
    private static final String SIZE_QUERY_PARAM_NAME = "size";

    // TODO add unit tests
    @NotNull public static <T> String buildPaginationLink(Page<T> page, String methodPath) {
        var firstPageLink = buildFirstPageLink(page, methodPath);
        var previousPageLink = buildPreviousPageLink(page, methodPath);
        var nextPageLink = buildNextPageLink(page, methodPath);
        var lastPageLink = buildLastPageLink(page, methodPath);
        return buildPaginationLink(firstPageLink, previousPageLink, nextPageLink, lastPageLink);
    }

    private static String buildPaginationLink(
            String firstPageLink,
            String previousPageLink,
            String nextPageLink,
            String lastPageLink) {
        return firstPageLink + previousPageLink + nextPageLink + lastPageLink;
    }

    @NotNull private static <T> String buildFirstPageLink(Page<T> page, String methodPath) {
        return buildLink(methodPath, 1, page.getSize(), FIRST_LINK_POSTFIX);
    }

    @NotNull private static <T> String buildPreviousPageLink(Page<T> page, String methodPath) {
        String previousPageLink;
        if ((page.getNumber()) > 0) {
            previousPageLink =
                    buildLink(methodPath, page.getNumber(), page.getSize(), PREV_LINK_POSTFIX);
        } else {
            previousPageLink = StringUtils.EMPTY;
        }
        return previousPageLink;
    }

    @NotNull private static <T> String buildNextPageLink(Page<T> page, String methodPath) {
        String nextPageLink;
        if ((page.getNumber() + 1) < page.getTotalPages()) {
            nextPageLink =
                    buildLink(methodPath, page.getNumber() + 2, page.getSize(), NEXT_LINK_POSTFIX);
        } else {
            nextPageLink = StringUtils.EMPTY;
        }
        return nextPageLink;
    }

    @NotNull private static <T> String buildLastPageLink(Page<T> page, String methodPath) {
        var lastPage = page.getTotalPages() > 0 ? page.getTotalPages() : 1;
        return buildLink(methodPath, lastPage, page.getSize(), LAST_LINK_POSTFIX);
    }

    @NotNull private static String buildLink(String methodPath, int page, int size, String postfix) {
        return LINK_PREFIX + generatePaginationUri(methodPath, page, size) + postfix;
    }

    private static String generatePaginationUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam(PAGE_QUERY_PARAM_NAME, page)
                .queryParam(SIZE_QUERY_PARAM_NAME, size)
                .toUriString();
    }
}
