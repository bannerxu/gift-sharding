package top.banner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`user`")
public class User {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private Date createTime;

    private Date updateTime;

    private Long balance;


}