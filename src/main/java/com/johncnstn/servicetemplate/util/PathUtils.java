package com.johncnstn.servicetemplate.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class PathUtils {

    private static final Pattern PATH_VARIABLES_PATTERN = Pattern.compile("\\{\\S+?}");

    public static String generatePath(String baseUrl, Object... pathVariables) {
        var replacements = new LinkedList<>(Arrays.asList(pathVariables));
        return PATH_VARIABLES_PATTERN
                .matcher(baseUrl)
                .replaceAll(matchResult -> String.valueOf(replacements.poll()));
    }
}
