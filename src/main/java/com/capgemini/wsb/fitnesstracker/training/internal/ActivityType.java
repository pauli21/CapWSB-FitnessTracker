package com.capgemini.wsb.fitnesstracker.training.internal;

/**
 * Enum reprezentujący różne rodzaje aktywności fizycznych.
 */
public enum ActivityType {

    RUNNING("Running"),
    CYCLING("Cycling"),
    WALKING("Walking"),
    SWIMMING("Swimming"),
    TENNIS("Tenis");

    /**
     * Wyświetlana nazwa aktywności.
     */
    private final String displayName;

    /**
     * Konstruktor dla enuma.
     *
     * @param displayName wyświetlana nazwa aktywności
     */
    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Pobiera wyświetlaną nazwę aktywności.
     *
     * @return nazwa aktywności jako {@link String}
     */
    public String getDisplayName() {
        return displayName;
    }

}
