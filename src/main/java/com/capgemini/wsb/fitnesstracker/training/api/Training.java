package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.ZoneId;


import java.util.Date;
@Entity
@Table(name = "trainings")
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "date", nullable = false)
    private LocalDate date; // Pole na rok i miesiąc w formacie "yyyy-MM"


    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    @Column(name = "distance")
    private double distance;

    @Column(name = "average_speed")
    private double averageSpeed;

    /**
     * Konstruktor klasy `Training` do utworzenia obiektu na podstawie danych o użytkowniku, czasie rozpoczęcia,
     * czasie zakończenia, typie aktywności, dystansie i średniej prędkości.
     *
     * @param user Użytkownik, który wykonał trening.
     * @param startTime Czas rozpoczęcia treningu.
     * @param endTime Czas zakończenia treningu.
     * @param activityType Typ aktywności (np. bieganie, jazda na rowerze).
     * @param distance Dystans pokonany podczas treningu.
     * @param averageSpeed Średnia prędkość podczas treningu.
     */
    public Training(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        this.date = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().withDayOfMonth(1); // ustawienie na pierwszy dzień miesiąca
    }

    /**
     * Ustawia dystans pokonany podczas treningu.
     *
     * @param distance Dystans pokonany podczas treningu.
     */
    public void setDistance(double distance) {
     this.distance = distance;
    }

    /**
     * Ustawia średnią prędkość podczas treningu.
     *
     * @param averageSpeed Średnia prędkość podczas treningu.
     */
    public void setAverageSpeed(double averageSpeed) {
     this.averageSpeed = averageSpeed;
    }


    /**
     * Ustawia czas zakończenia treningu oraz aktualizuje datę (na podstawie czasu zakończenia),
     * ustawiając ją na pierwszy dzień miesiąca.
     *
     * @param endTime Czas zakończenia treningu.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        this.date = endTime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .withDayOfMonth(1);
    }

}