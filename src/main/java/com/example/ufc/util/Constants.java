package com.example.ufc.util;

/**
 * Application-wide constants to avoid magic numbers and strings.
 * Following best practices for maintainability and consistency.
 */
public final class Constants {

    private Constants() {
        // Private constructor to prevent instantiation
    }

    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final int SEARCH_PAGE_SIZE = 15;
    public static final int ADMIN_PAGE_SIZE = 25;

    // Validation
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 100;
    public static final int EMAIL_MAX_LENGTH = 100;
    public static final int BIO_MAX_LENGTH = 500;
    public static final int TOPIC_TITLE_MIN_LENGTH = 5;
    public static final int TOPIC_TITLE_MAX_LENGTH = 200;
    public static final int POST_CONTENT_MIN_LENGTH = 10;
    public static final int POST_CONTENT_MAX_LENGTH = 10000;
    public static final int COMMENT_MIN_LENGTH = 1;
    public static final int COMMENT_MAX_LENGTH = 1000;

    // Reputation
    public static final int REPUTATION_NEW_TOPIC = 5;
    public static final int REPUTATION_NEW_POST = 2;
    public static final int REPUTATION_UPVOTE = 10;
    public static final int REPUTATION_DOWNVOTE = -2;
    public static final int REPUTATION_ACCEPTED_ANSWER = 15;

    // User Activity
    public static final int RECENT_USERS_COUNT = 10;
    public static final int RECENT_TOPICS_COUNT = 10;
    public static final int TOP_POSTERS_COUNT = 10;

    // Session & Security
    public static final int SESSION_TIMEOUT_MINUTES = 30;
    public static final int MAX_LOGIN_ATTEMPTS = 5;
    public static final int LOCKOUT_DURATION_MINUTES = 15;

    // File Upload
    public static final long MAX_AVATAR_SIZE = 5_242_880; // 5MB
    public static final long MAX_ATTACHMENT_SIZE = 10_485_760; // 10MB
    public static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/png", "image/gif", "image/webp"};

    // Cache Keys
    public static final String CACHE_CATEGORIES = "categories";
    public static final String CACHE_STATS = "stats";
    public static final String CACHE_POPULAR_TOPICS = "popularTopics";

    // Time Periods
    public static final int ONLINE_USER_THRESHOLD_MINUTES = 15;
    public static final int ACTIVE_USER_DAYS = 30;
    public static final int NEW_USER_DAYS = 7;
    public static final int HOT_TOPIC_DAYS = 3;

    // Error Messages
    public static final String ERROR_USER_NOT_FOUND = "Потребителят не беше намерен";
    public static final String ERROR_TOPIC_NOT_FOUND = "Темата не беше намерена";
    public static final String ERROR_POST_NOT_FOUND = "Постът не беше намерен";
    public static final String ERROR_ACCESS_DENIED = "Нямате права за достъп до този ресурс";
    public static final String ERROR_INVALID_CREDENTIALS = "Невалидно потребителско име или парола";
    public static final String ERROR_USER_ALREADY_EXISTS = "Потребител с това име или имейл вече съществува";

    // Success Messages
    public static final String SUCCESS_REGISTRATION = "Регистрацията беше успешна! Моля влезте с вашите данни.";
    public static final String SUCCESS_TOPIC_CREATED = "Темата беше създадена успешно!";
    public static final String SUCCESS_POST_CREATED = "Постът беше създаден успешно!";
    public static final String SUCCESS_PROFILE_UPDATED = "Профилът беше актуализиран успешно!";
}
