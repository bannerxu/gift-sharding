package top.banner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.banner.entity.GiftSendRecordSingle;

import java.util.List;

public interface GiftSendRecordSingleMapper extends BaseMapper<GiftSendRecordSingle> {

    int insertBatch(@Param("list") List<GiftSendRecordSingle> list);

}