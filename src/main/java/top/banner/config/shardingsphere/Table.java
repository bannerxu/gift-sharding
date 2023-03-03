package top.banner.config.shardingsphere;

import lombok.Data;

@Data
public class Table {
    private String name;
    private String engine;
    private String comment;
    private String createTime;
}
