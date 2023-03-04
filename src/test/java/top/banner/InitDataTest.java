package top.banner;

import cn.hutool.core.util.RandomUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import top.banner.config.RedisKey;
import top.banner.entity.GiftSendRecord;
import top.banner.entity.GiftSendRecordSingle;
import top.banner.entity.Room;
import top.banner.entity.User;
import top.banner.mapper.GiftSendRecordMapper;
import top.banner.mapper.GiftSendRecordSingleMapper;
import top.banner.mapper.RoomMapper;
import top.banner.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class InitDataTest {
    final Gson gson = new Gson();
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoomMapper roomMapper;

    @Resource
    private GiftSendRecordMapper giftSendRecordMapper;
    @Resource
    private GiftSendRecordSingleMapper giftSendRecordSingleMapper;


    /**
     * 随机一个时间戳
     */
    private static long random(long begin, long end) {
        return begin + (long) (Math.random() * (end - begin));
    }

    private static Date randomDate() {
        //2023-01-01 00:00:00  1672502400000  2023-02-01 00:00:00  1675180800000
        //2023-02-01 00:00:00  1675180800000  2023-03-01 00:00:00  1677600000000
        //2023-03-01 00:00:00  1677600000000  2023-04-01 00:00:00  1680278400000

        final long random = random(1677600000000L, 1680278400000L);
        return new Date(random);
    }


    @Test
    @Disabled
    public void initGiftSend() {
        final SetOperations<String, String> ops = stringRedisTemplate.opsForSet();

        for (int i = 0; i < 10000; i++) {
            final List<String> from = ops.randomMembers(RedisKey.userIds, 500);
            final List<String> to = ops.randomMembers(RedisKey.userIds, 500);

            final ArrayList<GiftSendRecord> giftSendRecords = new ArrayList<>();
            final ArrayList<GiftSendRecordSingle> giftSendRecordSingles = new ArrayList<>();
            for (int j = 0; j < Objects.requireNonNull(from).size(); j++) {
                final int giftNum = RandomUtil.randomInt(100);
                final GiftSendRecord record = GiftSendRecord.builder()
                        .uid(Long.parseLong(from.get(j)))
                        .receiveUid(Long.parseLong(Objects.requireNonNull(to).get(j)))
                        .roomId(Long.parseLong(to.get(j)))
                        .giftId(Integer.parseInt(ops.randomMember(RedisKey.giftIds)))
                        .giftNum(giftNum)
                        .totalGoldNum((long) giftNum)
                        .createTime(randomDate())
                        .build();
                giftSendRecords.add(record);
                final GiftSendRecordSingle recordSingle = new GiftSendRecordSingle();
                BeanUtils.copyProperties(record, recordSingle);
                giftSendRecordSingles.add(recordSingle);
            }
            giftSendRecordMapper.insertBatch(giftSendRecords);
            giftSendRecordSingleMapper.insertBatch(giftSendRecordSingles);
        }
    }

    @Test
    @Disabled
    public void initUserAndRoom() {
        final SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        final ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();

        for (int i = 0; i < 1000; i++) {
            List<String> names = ops.randomMembers(RedisKey.usernames, 100);
            if (names == null) {
                names = new ArrayList<>();
            }
            final List<User> collect = names.stream().map(name -> User.builder().username(name).password("123456").build()).collect(Collectors.toList());
            userMapper.installBatch(collect);


            final Map<String, String> map = collect.stream().collect(Collectors.toMap(it -> RedisKey.users(it.getId()), gson::toJson));
            opsForValue.multiSet(map);


            final List<Room> rooms = collect.stream().map(user -> Room.builder()
                    .title(user.getUsername() + "的房间")
                    .uid(user.getId())
                    .roomId(user.getId() + 10000)
                    .roomPwd("123456")
                    .roomDesc("大家来聊天呀")
                    .build()
            ).collect(Collectors.toList());
            roomMapper.installBatch(rooms);

            final Map<String, String> roomCache = rooms.stream().collect(Collectors.toMap(it -> RedisKey.rooms(it.getUid()), gson::toJson));
            opsForValue.multiSet(roomCache);
        }
    }
}
