package com.example.ufc.entity;

public enum TopicCategory {
    GENERAL_UFC("🥊 Общи дискусии за UFC", "Обсъждайте всичко свързано с UFC"),
    UPCOMING_EVENTS("📅 Предстоящи събития", "Информация за предстоящи UFC събития"),
    FIGHT_ANALYSIS("📊 Анализи и прогнози", "Споделете анализи и прогнози"),
    UFC_HISTORY("🏆 История на UFC", "Легендарни боеве и исторически моменти"),
    BULGARIAN_FIGHTERS("🇧🇬 Български бойци", "Следете българските бойци в UFC"),
    TRAINING_TIPS("💪 Тренировъчни съвети", "Споделете тренировъчни техники"),
    OFF_TOPIC("💬 Извън темата", "Общи разговори извън UFC");

    private final String displayName;
    private final String description;

    TopicCategory(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return displayName.substring(0, 2); // Extract emoji
    }

    public String getNameWithoutIcon() {
        return displayName.substring(3); // Remove emoji and space
    }
}