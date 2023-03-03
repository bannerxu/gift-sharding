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
@TableName(value = "gift_send_record")
public class GiftSendRecord {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 礼物发送人UID
     */
    private Long uid;

    /**
     * 礼物接收人UID
     */
    private Long receiveUid;

    /**
     * 礼物发送房间，send_env为1和3时需要记录
     */
    private Long roomId;

    /**
     * 礼物ID
     */
    private Integer giftId;

    private Integer giftNum;

    /**
     * 金币总额
     */
    private Long totalGoldNum;

    private Date createTime;


}