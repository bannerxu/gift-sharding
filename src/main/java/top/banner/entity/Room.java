package top.banner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "room")
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