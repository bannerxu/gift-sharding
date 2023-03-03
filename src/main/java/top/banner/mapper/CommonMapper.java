package top.banner.mapper;

import org.apache.ibatis.annotations.Param;
import top.banner.config.shardingsphere.CreateTableSql;

import java.util.List;

/**
 * 常用工具 mapper
 */
public interface CommonMapper {

	/**
	 * 查询数据库中的所有表名
	 *
	 * @param schema 数据库名
	 * @return 表名列表
	 */
	List<String> getAllTableNameBySchema(@Param("schema") String schema);

	/**
	 * 查询建表语句
	 *
	 * @param tableName 表名
	 * @return 建表语句
	 */
	CreateTableSql SelectTableCreateSql(@Param("tableName") String tableName);

	/**
	 * 执行SQL
	 *
	 * @param sql 待执行SQL
	 */
	void executeSql(@Param("sql") String sql);
}
