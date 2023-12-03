package HIM.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "review")
public class Review {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "review_id", nullable = false)
        private Long reviewId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "restaurant_id")
        private Restaurant restaurant;

        @Column(name = "context")
        private String context;

        @Column(name = "star_point")
        private Long starPoint;

        @Column(name = "review_thumbnail")
        private String reviewThumbnail;

        @Column(name = "is_deleted", nullable = false)
        private Boolean isDeleted;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private Instant createdAt;



}
