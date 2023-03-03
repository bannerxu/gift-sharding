package top.banner.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.banner.config.shardingsphere.CommonService;
import top.banner.config.shardingsphere.CreateTableSql;
import top.banner.mapper.CommonMapper;

/**
 * 分表工具
 */
@Slf4j
public class ShardingAlgorithmTool {


	@Setter
	private static CommonMapper commonMapper;
	@Setter
	private static CommonService commonService;
	@Setter
	private static StringRedisTemplate stringRedisTemplate;


	/**
	 * 判断 分表获取的表名是否存在 不存在则自动建表
	 *
	 * @param logicTableName  逻辑表名(表头)
	 * @param resultTableName 真实表名
	 * @return 确认存在于数据库中的真实表名
	 */
	public static String shardingTablesCheckAndCreatAndReturn(String logicTableName, String resultTableName) {

		try {
			synchronized (logicTableName.intern()) {
				// 缓存中有此表 返回
				if (shardingTablesExistsCheck(resultTableName)) {
					return resultTableName;
				}
				// 缓存中无此表 建表 并添加缓存
				CreateTableSql createTableSql = commonService.selectTableCreateSql(logicTableName);
				String sql = createTableSql.getCreateTable();
				sql = sql.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
				sql = sql.replace(logicTableName, resultTableName);
				commonMapper.executeSql(sql);

				stringRedisTemplate.opsForSet().add(RedisKey.dbTable, resultTableName);
			}
		} catch (Exception e) {
			log.error("获取分表信息 异常: ", e);
			return null;
		}

		return resultTableName;
	}

	/**
	 * 判断表是否存在于缓存中
	 *
	 * @param resultTableName 表名
	 * @return 是否存在于缓存中
	 */
	public static boolean shardingTablesExistsCheck(String resultTableName) {
		return Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(RedisKey.dbTable, resultTableName));
	}
}
