package HIM.project.entity;


import HIM.project.entity.type.Day;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uesr_id", nullable = false)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "user_thumbnail")
    private String userThumbnail;

    public static User of(User user, String nickName) {
        return User.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userThumbnail(user.getUserThumbnail())
                .userName(nickName)
                .build();
    }
    public static User MyThumbnailOf(User user, String Thumbnail) {
        return User.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userThumbnail(Thumbnail)
                .userName(user.getUserName())
                .build();
    }
}
