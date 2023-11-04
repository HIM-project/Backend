package HIM.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 60)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String userId;

    private String refreshToken;


}