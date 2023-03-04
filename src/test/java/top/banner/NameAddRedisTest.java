package top.banner;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.banner.config.RedisKey;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@SpringBootTest

public class NameAddRedisTest {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @SneakyThrows
    @Test
    @Disabled
    public void nameAddRedis() {
        //BufferedReader是可以按行读取文件
        final SetOperations<String, String> ops = stringRedisTemplate.opsForSet();

        try (FileInputStream inputStream = new FileInputStream("/Users/xgl/IdeaProjects/GiftSharding/src/main/resources/Chinese_Names_Corpus（120W）.txt")) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                ops.add(RedisKey.usernames, str);
            }
        }
    }
}
