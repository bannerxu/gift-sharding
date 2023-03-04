package top.banner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @TableId(value = "uid", type = IdType.INPUT)
    private Long uid;

    /**
     * 房间号
     */

    private Long roomId;

    /**
     * 进房间密码
     */
    private String roomPwd;

    private String title;

    /**
     * 房间话题
     */
    private String roomDesc;

    private Date createTime;

    private Date updateTime;


}