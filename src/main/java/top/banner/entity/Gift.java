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
@TableName(value = "gift")
public class Gift {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;


    private String name;

    private Long gold;

    /**
     * 1-有效 0-无效
     */
    private Byte status;

    private Date createTime;

    private Date updateTime;


}