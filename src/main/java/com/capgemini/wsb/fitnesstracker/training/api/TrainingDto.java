package com.capgemini.wsb.fitnesstracker.training.api;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;

/**
 * Obiekt transferu danych (DTO) reprezentujący informacje o treningu.
 */
public class TrainingDto {
    private User user;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;
    private String date;

    // Gettery i Settery
    /**
     * Pobiera użytkownika powiązanego z treningiem.
     *
     * @return użytkownik
     */
    public User getUser() {
        return user;
    }

    /**
     * Ustawia użytkownika powiązanego z treningiem.
     *
     * @param user użytkownik do ustawienia
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Pobiera datę treningu.
     *
     * @return data jako String
     */
    public String getDate() {
        return date;
    }

    /**
     * Ustawia datę treningu.
     *
     * @param date data do ustawienia jako String
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Pobiera czas rozpoczęcia treningu.
     *
     * @return czas rozpoczęcia jako obiekt {@link Date}
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Ustawia czas rozpoczęcia treningu.
     *
     * @param startTime czas rozpoczęcia do ustawienia
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    /**
     * Pobiera czas zakończenia treningu.
     *
     * @return czas zakończenia jako obiekt {@link Date}
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Ustawia czas zakończenia treningu.
     *
     * @param endTime czas zakończenia do ustawienia
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Pobiera rodzaj aktywności wykonywanej podczas treningu.
     *
     * @return rodzaj aktywności
     */
    public ActivityType getActivityType() {
        return activityType;
    }

    /**
     * Ustawia rodzaj aktywności wykonywanej podczas treningu.
     *
     * @param activityType rodzaj aktywności do ustawienia
     */
    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    /**
     * Pobiera dystans pokonany podczas treningu.
     *
     * @return dystans w kilometrach
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Ustawia dystans pokonany podczas treningu.
     *
     * @param distance dystans w kilometrach do ustawienia
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Pobiera średnią prędkość podczas treningu.
     *
     * @return średnia prędkość w kilometrach na godzinę
     */
    public double getAverageSpeed() {
        return averageSpeed;
    }

    /**
     * Ustawia średnią prędkość podczas treningu.
     *
     * @param averageSpeed średnia prędkość w kilometrach na godzinę do ustawienia
     */
    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
