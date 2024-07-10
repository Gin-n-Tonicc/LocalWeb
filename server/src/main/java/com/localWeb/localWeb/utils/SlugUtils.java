package com.localWeb.localWeb.utils;

import java.util.regex.Pattern;

public class SlugUtils {

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("[\\s_]+");

    public static String generateSlug(String input) {
        if (input == null) {
            return null;
        }

        // Convert to lowercase
        String slug = input.toLowerCase().trim();

        // Replace spaces and underscores with hyphens
        slug = WHITESPACE_PATTERN.matcher(slug).replaceAll("-");

        // Remove leading/trailing hyphens
        slug = slug.replaceAll("^-+|-+$", "");

        // Ensure slug is not empty
        if (slug.isEmpty()) {
            throw new IllegalArgumentException("Could not generate slug from input: " + input);
        }

        return slug;
    }
}
