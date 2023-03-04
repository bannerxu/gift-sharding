package top.banner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.banner.entity.Room;

import java.util.List;

public interface RoomMapper extends BaseMapper<Room> {
    int installBatch(@Param("rooms") List<Room> rooms);
}