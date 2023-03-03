package top.banner.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.banner.config.shardingsphere.CommonService;
import top.banner.mapper.CommonMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目启动后 读取已有分表 进行缓存
 */
@Slf4j
@Order(value = 1) // 数字越小 越先执行
@Component
public class ShardingTablesLoadRunner implements CommandLineRunner {

	@Resource
	private CommonMapper commonMapper;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Resource
	private CommonService commonService;

	@Override
	public void run(String... args) throws Exception {

		// 给 分表工具类注入属性
		ShardingAlgorithmTool.setStringRedisTemplate(stringRedisTemplate);
		ShardingAlgorithmTool.setCommonMapper(commonMapper);
		ShardingAlgorithmTool.setCommonService(commonService);

		// 读取数据库中所有表名
		List<String> tableNameList = commonService.getlist();
		// 删除旧的缓存(如果存在)
		if (tableNameList.isEmpty()) {
			return;
		}
		stringRedisTemplate.delete(RedisKey.dbTable);

		// 写入新的缓存
		stringRedisTemplate.opsForSet().add(RedisKey.dbTable, tableNameList.toArray(new String[0]));
		log.info("ShardingTablesLoadRunner start OK");
	}
}
