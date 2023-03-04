package top.banner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.banner.entity.GiftSendRecord;

import java.util.List;

public interface GiftSendRecordMapper extends BaseMapper<GiftSendRecord> {
    int insertBatch(@Param("list") List<GiftSendRecord> list);
}