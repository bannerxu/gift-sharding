package top.banner;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.banner.entity.GiftSendRecord;
import top.banner.mapper.GiftSendRecordMapper;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
public class GiftSendRecordTest {
    @Resource
    private GiftSendRecordMapper giftSendRecordMapper;

    @Test
    @Disabled
    public void test() {
        giftSendRecordMapper.insert(new GiftSendRecord(1L, 1L, 1L, 1L, 1, 1, 10L, new Date()));
    }
}
