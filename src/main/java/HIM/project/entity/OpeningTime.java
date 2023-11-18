package HIM.project.entity;


import HIM.project.entity.type.Day;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
@Builder
@Entity
@ToString
@Setter
@Table(name = "opening_time")
public class OpeningTime {
    @Column(name = "opening_time_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long openingTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "day")
    private String day;

    @Column(name = "open")
    private Time opeingTime;

    @Column(name = "close")
    private Time closeTime;

    @Column(name = "break_open")
    private Time breakOpen;

    @Column(name = "break_close")
    private Time breakClose;

    public static OpeningTime of(Day day, Restaurant restaurant){
        return OpeningTime.builder()
                .day(day.getValue())
                .opeingTime(null)
                .closeTime(null)
                .restaurant(restaurant)
                .build();
    }
}
