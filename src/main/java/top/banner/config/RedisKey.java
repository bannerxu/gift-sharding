package top.banner.config;

public class RedisKey {


    public static String dbTable = "db:name";


    public static String usernames = "sys:usernames";
    public static String userIds = "users:ids";
    public static String giftIds = "gifts:ids";

    public static String users(Long uid) {
        return "users:" + uid;
    }

    public static String rooms(Long uid) {
        return "rooms:" + uid;
    }
}
