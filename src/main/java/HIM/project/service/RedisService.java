package HIM.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    private final Long expirationTime = 604800L;

    public void setValues(String refreshToken, String userId) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(userId, refreshToken, expirationTime, TimeUnit.SECONDS);
    }

    public void setValues(String accessToken, Long expirationTime) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(accessToken, accessToken, expirationTime, TimeUnit.SECONDS);
    }
    public void setConfirmNum(String userId, String time) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(userId, time, expirationTime, TimeUnit.SECONDS);
    }

    public void setUserPermission(String userId, String permission) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(userId, permission, expirationTime, TimeUnit.SECONDS);
    }

    public String getValues(String userId) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(userId);
    }
    public String getConfirmTime(String userId) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(userId);
    }
    public boolean deleteDictionary(String userId) {
        Boolean delete = redisTemplate.delete(userId);
        return Boolean.TRUE.equals(delete);
    }
}
