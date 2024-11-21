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

//    private Long userId;
//
//    private LocalDate date;


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
    public void setDistance(double distance) {
     this.distance = distance;
    }

    public void setAverageSpeed(double averageSpeed) {
     this.averageSpeed = averageSpeed;
    }


//    // Aktualizacja `date` na podstawie `endTime`
//    public void updateDateFromEndTime() {
//        if (this.endTime != null) {
//            this.date = new java.text.SimpleDateFormat("yyyy-MM").format(this.endTime);
//        }
//    }

//    // Setter z automatyczną aktualizacją `date`
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//        this.date = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().withDayOfMonth(1); // aktualizacja pola date
//    }

        // Setter z automatyczną aktualizacją date
        public void setEndTime(Date endTime) {
            this.endTime = endTime;
            this.date = endTime.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .withDayOfMonth(1);
        }

}