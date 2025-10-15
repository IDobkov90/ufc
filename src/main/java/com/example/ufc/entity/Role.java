package com.example.ufc.entity;

public enum Role {
    USER("User"),
    MODERATOR("Moderator"),
    ADMIN("Administrator");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean hasPermission(String permission) {
        return switch (this) {
            case ADMIN -> true; // Admin has all permissions
            case MODERATOR -> permission.startsWith("MODERATE_") || permission.startsWith("VIEW_");
            case USER -> permission.startsWith("VIEW_") || permission.startsWith("CREATE_");
        };
    }
}