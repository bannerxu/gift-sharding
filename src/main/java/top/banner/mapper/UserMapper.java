package top.banner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.banner.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    int installBatch(@Param("users") List<User> users);
}